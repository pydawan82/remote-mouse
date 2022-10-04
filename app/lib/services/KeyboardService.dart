import 'package:app/dto/keyboard_input.dart';
import 'package:get/get.dart';

import 'package:app/api/keyboard.dart' as api;

class KeyboardService extends GetxService {
  KeyboardInputDto _fromInt(int code) {
    return KeyboardInputDto(keyCode: code);
  }

  List<KeyboardInputDto> _fromString(String text) {
    return text.runes.map((e) => KeyboardInputDto(keyCode: e)).toList();
  }

  Future<void> press(int key) async {
    final dto = _fromInt(key);
    await api.press(dto);
  }

  Future<void> release(int key) async {
    final dto = _fromInt(key);
    await api.release(dto);
  }

  Future<void> type(String text) async {
    final inputs = _fromString(text);
    for (final input in inputs) {
      await api.type(input);
    }
  }
}
