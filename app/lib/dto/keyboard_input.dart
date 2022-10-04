import 'dto_base.dart';

class KeyboardInputDto with DtoBase {
  int keyCode;

  KeyboardInputDto({required this.keyCode});

  @override
  Map<String, dynamic> toMap() => {
        'keyCode': keyCode,
      };
}
