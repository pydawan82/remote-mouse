import 'package:app/http/authorization.dart';
import 'package:app/http/http_service.dart';
import 'package:app/screens/cursor_page.dart';
import 'package:app/services/keyboard_service.dart';
import 'package:app/services/mouse_service.dart';
import 'package:app/services/log_service.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import 'services/event_pipe_service.dart';

void main() {
  _registerServices();
  runApp(const MyApp());
}

void _registerServices() {
  Get.put(
    HttpService(
      Uri.parse('http://192.168.1.23:8080'),
      CookieAndBasicAuthentication(
        username: 'david',
        password: 'zeushera',
      ),
    ),
  );
  Get.put(MouseService());
  Get.put(KeyboardService());
  Get.put(LogService());
  Get.put(EventPipeService());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: CursorPage(),
      bottomNavigationBar: BottomNavigationBar(items: [
        BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
        BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
      ]),
    );
  }
}
