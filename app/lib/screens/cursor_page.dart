import 'package:app/models/button.dart';
import 'package:app/models/mouse.dart';
import 'package:app/services/MouseService.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class CursorPage extends StatelessWidget {
  static const _button_height = 50.0;

  final Mouse mouse = Mouse();

  @override
  Widget build(BuildContext context) {
    return Column(
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
