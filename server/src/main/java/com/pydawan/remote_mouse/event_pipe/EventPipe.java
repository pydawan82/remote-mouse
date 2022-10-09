package com.pydawan.remote_mouse.event_pipe;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.javatuples.Pair;

import com.pydawan.remote_mouse.mouse.Mouse;
import com.pydawan.remote_mouse.mouse.MouseButton;
import com.pydawan.remote_mouse.vk.VirtualKey;
import com.pydawan.remote_mouse.vk.VirtualKeyboard;

public class EventPipe {

    public static final int KEY_LENGTH = 32;
    private static final long MAX_WAIT_TIME = 10000L;
    private static final int TIMEOUT = 5000;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Future<?> future;
    private Socket socket;
    private Scanner in;

    private final Mouse mouse = Mouse.getInstance();
    private final VirtualKeyboard keyboard = VirtualKeyboard.getInstance();

    private final Map<Short, VirtualKey> keyMap;

    public EventPipe() {
        keyMap = new HashMap<>(VirtualKey.values().length);
        Stream.of(VirtualKey.values())
                .forEach(key -> keyMap.putIfAbsent(key.getValue(), key));
    }

    public EventPipe(InputStream in) {
        this();

        this.in = new Scanner(in);

    }

    private EventPipe(ServerSocket socket, byte[] key) {
        this();

        executor.submit(() -> this.runWithSocket(socket, key));
    }

    private void runWithSocket(ServerSocket socket, byte[] key) {
        waitForConnection(socket, key);
        listen();
        try {
            close();
        } catch (Exception e) {
            // Do nothing
        }
    }

    private void waitForConnection(ServerSocket server, byte[] key) {
        try {
            waitForConnection_(server, key);
        } catch (IOException e) {
            // Do nothing
        }
    }

    private void waitForConnection_(ServerSocket server, byte[] key) throws IOException {
        try {
            server.setSoTimeout(TIMEOUT);

            long startTime = System.currentTimeMillis();

            while (System.currentTimeMillis() - startTime < MAX_WAIT_TIME) {
                if (handleConnection(server.accept(), key))
                    break;
            }

        } finally {
            server.close();
        }
    }

    private boolean handleConnection(Socket socket, byte[] key) throws IOException {
        socket.setSoTimeout(TIMEOUT);
        InputStream in = socket.getInputStream();
        byte[] givenKey;
        try {
            givenKey = in.readNBytes(KEY_LENGTH);
        } catch (IOException e) {
            return false;
        }

        if (!Arrays.equals(key, givenKey)) {
            socket.close();
            return false;
        }

        this.in = new Scanner(in);
        this.socket = socket;
        socket.setSoTimeout(0);

        return true;
    }

    private static ServerSocket tryOpenServer(Supplier<Short> portSupplier) {
        ServerSocket socket = null;
        for (int i = 0; i < 100; i++) {
            try {
                socket = new ServerSocket(Short.toUnsignedInt(portSupplier.get()));
                break;
            } catch (IOException | SecurityException | IllegalArgumentException e) {
                // Port is already in use
            }
        }

        if (socket == null)
            throw new NoSuchElementException(new IOException("Could not open server"));

        return socket;
    }

    public static short openServer(Supplier<Short> portSupplier, byte[] key) {
        ServerSocket socket = tryOpenServer(portSupplier);
        short port = (short) socket.getLocalPort();

        new EventPipe(socket, key);

        return port;
    }

    public void listen() {
        if (future != null && !future.isDone())
            throw new IllegalStateException("Already listening");

        future = executor.submit(this::handleEvents);
    }

    public void stop() {
        if (future == null || future.isDone())
            throw new IllegalStateException("Not listening");

        future.cancel(true);
    }

    private Pair<InputEvent, String[]> parseEvent(String event) {
        String[] parts = event.split(" ");
        InputEvent inputEvent = InputEvent.valueOf(parts[0]);
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);

        return Pair.with(inputEvent, args);
    }

    private void handleEvents() {
        in.forEachRemaining(this::handleEvent);
    }

    private void handleEvent(String event) {
        Pair<InputEvent, String[]> parsedEvent;

        try {
            parsedEvent = parseEvent(event);
        } catch (IllegalArgumentException e) {
            return;
        }

        InputEvent inputEvent = parsedEvent.getValue0();
        String[] args = parsedEvent.getValue1();

        Consumer<String[]> consumer = eventHandler(inputEvent);

        consumer.accept(args);
    }

    private Consumer<String[]> eventHandler(InputEvent event) {
        return switch (event) {
            case MOUSE_MOVE -> this::handleMouseMove;
            case MOUSE_CLICK -> this::handleMouseClick;
            case MOUSE_SCROLL -> this::handleMouseScroll;
            case MOUSE_HSCROLL -> this::handleMouseHScroll;
            case KEY_PRESS -> this::handleKeyPress;
            case KEY_RELEASE -> this::handleKeyRelease;
            case KEY_TYPE -> this::handleKeyType;
        };
    }

    private void assertArgCount(String[] args, int count) {
        if (args.length != count)
            throw new IllegalArgumentException("Invalid argument count");
    }

    private void handleMouseMove(String... args) {
        assertArgCount(args, 2);

        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);

        mouse.move(x, y);
    }

    private void handleMouseClick(String... args) {
        assertArgCount(args, 1);

        int button = Integer.parseInt(args[0]);
        MouseButton mbutton = MouseButton.values()[button];

        mouse.click(mbutton);
    }

    private void handleMouseScroll(String... args) {
        assertArgCount(args, 1);

        int amount = Integer.parseInt(args[0]);

        mouse.scroll(amount);
    }

    private void handleMouseHScroll(String... args) {
        assertArgCount(args, 1);

        int amount = Integer.parseInt(args[0]);

        mouse.hscroll(amount);
    }

    private void handleKeyPress(String... args) {
        assertArgCount(args, 1);

        short keyCode = Short.parseShort(args[0]);
        VirtualKey vk = keyMap.get(keyCode);

        keyboard.sendKeyDown(vk);
    }

    private void handleKeyRelease(String... args) {
        assertArgCount(args, 1);

        short keyCode = Short.parseShort(args[0]);
        VirtualKey vk = keyMap.get(keyCode);

        keyboard.sendKeyUp(vk);
    }

    private void handleKeyType(String... args) {
        assertArgCount(args, 1);

        short keyCode = Short.parseShort(args[0]);
        VirtualKey vk = keyMap.get(keyCode);

        keyboard.pressKeys(vk);
    }

    public void close() throws Exception {
        if (socket != null)
            socket.close();
    }
}
