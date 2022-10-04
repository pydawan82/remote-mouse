import 'dart:ui';

import 'package:app/util/date_extension.dart';
import 'package:app/services/MouseService.dart';
import 'package:get/get.dart';

class Mouse {
  static const min_request_interval = Duration(milliseconds: 30);
  static const max_request_interval = Duration(milliseconds: 500);

  final MouseService _mouseService = Get.find();

  var _position_offset = Offset.zero;
  var _last_position_request = DateTime.now();
  Future<void>? _position_future;
  var _scroll_offset = Offset.zero;
  var _last_scroll_request = DateTime.now();

  Offset _decimalPart(Offset offset) {
    return Offset(offset.dx - offset.dx.floorToDouble(),
        offset.dy - offset.dy.floorToDouble());
  }

  Future<void> move(Offset offset) async {
    _position_offset += offset;
    Duration delay =
        min_request_interval - (DateTime.now() - _last_position_request);
    _position_future ??= Future.delayed(delay, () => _sendPositionRequest());
  }

  Future<void> _sendPositionRequest() async {
    _last_position_request = DateTime.now();
    final offset = _position_offset;
    final future = _position_future;

    _position_offset = _decimalPart(_position_offset);
    _position_future = null;

    await _mouseService.move(offset);

    if (_position_future == future) {
      _position_future = null;
    }
  }
}
