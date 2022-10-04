import 'dart:convert';

mixin DtoBase {
  Map<String, dynamic> toMap();
  String toJson() => json.encode(toMap());
}
