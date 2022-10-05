import 'package:app/models/button.dart';
import 'package:app/models/event_pipe.dart';
import 'package:app/models/mouse.dart';
import 'package:flutter/material.dart';

class CursorPage extends StatelessWidget {
  static const _button_height = 50.0;

  final Mouse mouse = PipeMouse.fromFuture(EventPipe.open());

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Expanded(
          child: Column(
            children: [
              Expanded(
                child: GestureDetector(
                  onPanUpdate: (details) => _mouseMove(details),
                  onDoubleTapDown: (details) => _mouseDown(Button.right),
                  onDoubleTapCancel: () => _mouseUp(Button.right),
                  onDoubleTap: () => _mouseUp(Button.right),
                  onTapDown: (details) => _mouseDown(Button.left),
                  onTapCancel: () => _mouseUp(Button.left),
                  onTap: () => _mouseUp(Button.left),
                  child: Container(
                    // a rounded rectangle border
                    decoration: BoxDecoration(
                      border: Border.all(color: Colors.black),
                      borderRadius: BorderRadius.circular(10),
                    ),
                    width: double.infinity,
                    height: double.infinity,
                  ),
                ),
              ),
              Row(
                children: [
                  Expanded(
                    flex: 4,
                    child: OutlinedButton(
                      onPressed: () => _mouseClick(Button.left),
                      child: const SizedBox(
                        height: _button_height,
                      ),
                    ),
                  ),
                  Expanded(
                    child: OutlinedButton(
                      onPressed: () => _mouseClick(Button.middle),
                      child: const SizedBox(
                        height: _button_height,
                      ),
                    ),
                  ),
                  Expanded(
                    flex: 4,
                    child: OutlinedButton(
                      onPressed: () => _mouseClick(Button.right),
                      child: const SizedBox(
                        height: _button_height,
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
        GestureDetector(
          onVerticalDragUpdate: (details) => mouse.scroll(details.delta),
          child: Container(
            width: 50,
            height: double.infinity,
            color: Colors.grey,
          ),
        )
      ],
    );
  }

  void _mouseMove(DragUpdateDetails details) {
    mouse.move(details.delta);
  }

  void _mouseDown(Button button) {
    print('Mouse down $button');
  }

  void _mouseUp(Button button) {
    print('Mouse up $button');
  }

  void _mouseClick(Button button) {
    //_mouseService.click(button);
  }
}
