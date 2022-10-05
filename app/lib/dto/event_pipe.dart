import 'package:app/dto/dto_base.dart';

class PipeInfo with DtoBase {
  final int port;
  final String key;

  PipeInfo(this.port, this.key);
  PipeInfo.fromJson(Map<String, dynamic> json)
      : port = json['port'],
        key = json['key'];

  @override
  Map<String, dynamic> toMap() => {
        'port': port,
        'key': key,
      };
}
