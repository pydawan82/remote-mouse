import 'package:app/dto/dto_base.dart';

class OffsetDto with DtoBase {
  final int x;
  final int y;

  OffsetDto({required this.x, required this.y});

  @override
  Map<String, dynamic> toMap() => {
        'x': x,
        'y': y,
      };
}
