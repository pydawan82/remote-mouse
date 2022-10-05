import 'package:app/dto/event_pipe.dart';
import 'package:app/http/http_service.dart';
import 'package:app/http/util.dart';
import 'package:get/instance_manager.dart';

Future<PipeInfo> openEventPipe() async {
  final HttpService http = Get.find();
  const path = '/event-pipe/open';

  final response = await http.get(path);
  return response.dto(PipeInfo.fromJson);
}
