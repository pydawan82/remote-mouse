import 'dart:ui';

import 'package:app/models/event_pipe.dart';
import 'package:app/util/date_extension.dart';
import 'package:app/services/mouse_service.dart';
import 'package:get/get.dart';

abstract class Mouse {
  dynamic move(Offset offset);
  dynamic scroll(Offset offset);

  Offset _decimalPart(Offset offset) {
    return Offset(offset.dx - offset.dx.floorToDouble(),
        offset.dy - offset.dy.floorToDouble());
  }
}

class PipeMouse extends Mouse {
  late final EventPipe _pipe;
  static const min_request_interval = Duration(milliseconds: 10);

  Offset _last_move_offset = Offset.zero;
  DateTime _last_move_request = DateTime.now();
  Future? _move_request = null;

  Offset _last_scroll_offset = Offset.zero;
  DateTime _last_scroll_request = DateTime.now();
  Future? _scroll_request = null;

  PipeMouse(this._pipe);

  PipeMouse.fromFuture(Future<EventPipe> pipe) {
    _move_request = pipe.then((value) {
      _pipe = value;
      _move_request = null;
    });
  }

  @override
  void move(Offset offset) {
    _last_move_offset += offset;
    Duration delay =
        min_request_interval - (DateTime.now() - _last_move_request);
    _move_request ??= Future.delayed(delay, () => _sendMove());
  }

  void _sendMove() {
    _pipe.mouseMove(_last_move_offset.dx.toInt(), _last_move_offset.dy.toInt());
    _last_move_offset = _decimalPart(_last_move_offset);
    _last_move_request = DateTime.now();
    _move_request = null;
  }

  void scroll(Offset offset) {
    _last_scroll_offset += offset;
    Duration delay =
        min_request_interval - (DateTime.now() - _last_scroll_request);
    _scroll_request ??= Future.delayed(delay, () => _sendScroll());
  }

  void _sendScroll() {
    _pipe.mouseScroll(
        _last_scroll_offset.dx.toInt(), _last_scroll_offset.dy.toInt());
    _last_scroll_offset = _decimalPart(_last_scroll_offset);
    _last_scroll_request = DateTime.now();
    _scroll_request = null;
  }
}

class HttpMouse extends Mouse {
  static const min_request_interval = Duration(milliseconds: 30);
  static const max_request_interval = Duration(milliseconds: 500);

  final MouseService _mouseService = Get.find();

  var _position_offset = Offset.zero;
  var _last_position_request = DateTime.now();
  Future<void>? _position_future;
  var _scroll_offset = Offset.zero;
  var _last_scroll_request = DateTime.now();

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

  @override
  Future<void> scroll(Offset offset) async {
    _scroll_offset += offset;
    Duration delay =
        min_request_interval - (DateTime.now() - _last_scroll_request);
    //_position_future ??= Future.delayed(delay, () => _sendScrollRequest());
  }
}
