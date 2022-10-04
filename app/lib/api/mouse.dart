import 'package:app/dto/button.dart';
import 'package:app/dto/offset.dart';
import 'package:get/get.dart';

import '../http/http_service.dart';

Future<void> click(ButtonDto button) async {
  HttpService http = Get.find();
  const path = '/mouse/click';
  await http.post(path, button);
}

Future<void> move(OffsetDto offset) async {
  HttpService http = Get.find();
  const path = '/mouse/move';
  await http.post(path, offset);
}

Future<void> scroll(OffsetDto offset) async {
  HttpService http = Get.find();
  const path = '/mouse/scroll';
  await http.post(path, offset);
}
