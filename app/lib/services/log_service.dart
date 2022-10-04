import 'package:get/get.dart';
import 'package:logger/logger.dart';

class LogService extends GetxService {
  bool verbose = false;
  final Logger logger = Logger();
}
