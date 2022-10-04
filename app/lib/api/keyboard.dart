import 'package:app/dto/keyboard_input.dart';
import 'package:app/http/http_service.dart';
import 'package:get/get.dart';

Future<void> press(KeyboardInputDto keyboardInput) async {
  HttpService http = Get.find();
  const path = '/keyboard/press';
  await http.post(path, keyboardInput);
}

Future<void> release(KeyboardInputDto keyboardInput) async {
  HttpService http = Get.find();
  const path = '/keyboard/release';
  await http.post(path, keyboardInput);
}

Future<void> type(KeyboardInputDto keyboardInput) async {
  HttpService http = Get.find();
  const path = '/keyboard/type';
  await http.post(path, keyboardInput);
}
