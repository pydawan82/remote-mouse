import 'dto_base.dart';

class ButtonDto with DtoBase {
  int button;

  ButtonDto({required this.button});

  @override
  Map<String, dynamic> toMap() => {
        'button': button,
      };
}
