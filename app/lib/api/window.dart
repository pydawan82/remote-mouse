import 'package:app/dto/window.dart';
import 'package:app/http/http_service.dart';
import 'package:app/http/util.dart';
import 'package:get/get.dart';

Future<List<WindowInfoDto>> windowInfos() async {
  HttpService service = Get.find();
  const path = '/window/list';
  final response = await service.get(path);
  return response.dtoList(WindowInfoDto.fromJson);
}
