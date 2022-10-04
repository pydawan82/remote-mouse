import 'dart:ui';

import 'package:app/dto/button.dart';
import 'package:app/dto/offset.dart';
import 'package:app/http/http_service.dart';
import 'package:app/models/button.dart';
import 'package:get/get.dart';

import 'package:app/api/mouse.dart' as api;

class MouseService extends GetxService {
  OffsetDto _fromOffset(Offset offset) {
    return OffsetDto(x: offset.dx.toInt(), y: offset.dy.toInt());
  }

  ButtonDto _fromButton(Button button) {
    return ButtonDto(button: button.index);
  }

  Future<void> move(Offset offset) async {
    final dto = _fromOffset(offset);
    await api.move(dto);
  }

  Future<void> click(Button button) async {
    final dto = _fromButton(button);
    await api.click(dto);
  }

  Future<void> scroll(Offset offset) async {
    final dto = _fromOffset(offset);
    await api.scroll(dto);
  }
}
