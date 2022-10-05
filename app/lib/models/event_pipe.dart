import 'dart:convert';
import 'dart:io';

import 'package:app/services/event_pipe_service.dart';
import 'package:get/get.dart';

class EventType {
  static const mouseMove = 'MOUSE_MOVE';
  static const mouseClick = 'MOUSE_CLICK';
  static const mouseScroll = 'MOUSE_SCROLL';
  static const keyPress = 'KEY_PRESS';
  static const keyRelease = 'KEY_RELEASE';
  static const keyType = 'KEY_TYPE';
}

class EventPipe {
  final Socket _socket;
  final _encoder = Utf8Encoder();

  EventPipe._(this._socket);

  static Future<EventPipe> open() async {
    final EventPipeService service = Get.find();
    final socket = await service.openEventPipe();
    return EventPipe._(socket);
  }

  Future<void> close() async {
    await _socket.close();
  }

  void _send(String message) {
    _socket.add(_encoder.convert(message));
    _socket.flush();
  }

  String _format(String cmd, List<String> args) {
    return '$cmd ${args.join(' ')}\n';
  }

  void mouseMove(int dx, int dy) {
    _send(_format(EventType.mouseMove, [dx.toString(), dy.toString()]));
  }

  void mouseClick(int button) {
    _send(_format(EventType.mouseClick, [button.toString()]));
  }

  void mouseScroll(int dx, int dy) {
    _send(_format(EventType.mouseScroll, [dx.toString(), dy.toString()]));
  }

  void keyPress(int key) {
    _send(_format(EventType.keyPress, [key.toString()]));
  }

  void keyRelease(int key) {
    _send(_format(EventType.keyRelease, [key.toString()]));
  }

  void keyType(String text) {
    _send(_format(EventType.keyType, [text]));
  }
}
