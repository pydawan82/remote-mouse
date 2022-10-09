package com.pydawan.remote_mouse.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.pydawan.remote_mouse.event_pipe.EventPipe;
import com.pydawan.remote_mouse.model.PipeInfo;

@Service
public class EventPipeService {

    private SecureRandom random = new SecureRandom();

    private Supplier<Short> portSupplier = () -> (short) (Math.random() * 10000 + 10000);
    private Supplier<byte[]> keySupplier = this::generateKey;
    private Function<byte[], String> keyEncoder = Base64.getEncoder()::encodeToString;

    private byte[] generateKey() {
        byte[] key = new byte[EventPipe.KEY_LENGTH];
        random.nextBytes(key);
        return key;
    }

    public PipeInfo open() {
        byte[] key = keySupplier.get();
        short port = EventPipe.openServer(portSupplier, key);
        String keyString = keyEncoder.apply(key);

        return new PipeInfo(Short.toUnsignedInt(port), keyString);
    }
}
