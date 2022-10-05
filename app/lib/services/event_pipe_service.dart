import 'dart:convert';
import 'dart:io';

import 'package:get/get.dart';

import 'package:app/api/event_pipe.dart' as api;

class EventPipeService extends GetxService {
  Future<Socket> openEventPipe() async {
    final pipeInfo = await api.openEventPipe();
    final socket = await Socket.connect('192.168.1.23', pipeInfo.port);
    socket.add(base64Decode(pipeInfo.key));
    socket.flush();

    return socket;
  }
}
