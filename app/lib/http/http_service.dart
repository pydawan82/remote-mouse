import 'dart:convert';
import 'dart:io';
import 'package:app/dto/dto_base.dart';
import 'package:app/http/util.dart';
import 'package:app/services/log_service.dart';
import 'package:get/get_utils/get_utils.dart';
import 'package:get/instance_manager.dart';
import 'package:http/http.dart';

import 'package:app/http/authorization.dart';
import 'package:logger/logger.dart';

import 'exceptions.dart';

class HttpService {
  static const _defaultContentType = 'application/json';

  final Uri baseUrl;
  final Authentication _authentication;

  const HttpService(
    this.baseUrl,
    this._authentication,
  );

  /// Returns a log method depending on the response status code.
  Function(String) getLogMethod(BaseResponse response, Logger logger) {
    if (response.isOk || response.isInfo || response.isRedirect) {
      return logger.i;
    } else if (response.isClientError) {
      return logger.w;
    } else if (response.isServerError) {
      return logger.e;
    } else {
      return logger.wtf;
    }
  }

  /// Throws an [HttpException] if the response code is greater than 400.
  void throwIfNotOk(BaseResponse response) {
    if (response.isInfo) return;
    if (response.isOk) return;
    if (response.isRedirect) return;
    if (response.statusCode == 400) {
      throw ClientErrorException(
          response.statusCode, response.reasonPhrase ?? '');
    }
    if (response.statusCode == 401) {
      throw UnauthorizedException(
          response.statusCode, response.reasonPhrase ?? '');
    }
    if (response.statusCode == 403) {
      throw ForbiddenException(
          response.statusCode, response.reasonPhrase ?? '');
    }
    if (response.statusCode == 404) {
      throw NotFoundException(response.statusCode, response.reasonPhrase ?? '');
    }
    if (response.statusCode == 409) {
      throw ConflictException(response.statusCode, response.reasonPhrase ?? '');
    }
    if (response.isServerError) {
      throw InternalServerErrorException(
          response.statusCode, response.reasonPhrase ?? '');
    }
    throw HttpException(response.statusCode, response.reasonPhrase ?? '');
  }

  static const jsonEncoder = JsonEncoder.withIndent('  ');

  /// Returns a message to log for the given request/response.
  ///
  /// More details are provided if the response is not ok.
  String logMessage(BaseRequest request, BaseResponse response, bool verbose) {
    StringBuffer buffer = StringBuffer();
    buffer.write('${request.method} ${request.url}');
    if ((!response.isOk || verbose)) {
      if (request is Request && !request.body.isBlank!) {
        buffer
            .write('\nBody: ${jsonEncoder.convert(json.decode(request.body))}');
      }
    }
    buffer.write('\nResponse: ${response.statusCode} ${response.reasonPhrase}');

    if (response is Response) {
      if (!response.isOk || verbose) {
        if (!response.body.isBlank! && request.accept == 'application/json') {
          buffer.write('\n${jsonEncoder.convert(response.json)}');
        }
      }
    }

    return buffer.toString();
  }

  Future<StreamedResponse> baseRequest(BaseRequest request) async {
    final LogService logService = Get.find();
    final logger = logService.logger;

    final response = await request.send();

    try {
      var logMethod = getLogMethod(response, logger);
      logMethod(logMessage(request, response, logService.verbose));
    } on FormatException {}

    throwIfNotOk(response);
    return response;
  }

  Future<Response> baseRequest2(BaseRequest request) async {
    final streamed = await baseRequest(request);
    return Response.fromStream(streamed);
  }

  Future<StreamedResponse> authenticatedRequest(BaseRequest request) async {
    _authentication.addAuthorization(request);
    final response = await baseRequest(request);
    _authentication.updateAuthorization(response);
    return response;
  }

  Future<Response> authenticatedRequest2(BaseRequest request) async {
    final streamed = await authenticatedRequest(request);
    return Response.fromStream(streamed);
  }

  Uri _resolveReference(dynamic reference) {
    late final Uri refUri;

    switch (reference.runtimeType) {
      case String:
        refUri = Uri.parse(reference);
        break;
      case Uri:
        refUri = reference;
        break;
      default:
        throw ArgumentError('Invalid reference type: ${reference.runtimeType}');
    }

    return baseUrl.resolveUri(refUri);
  }

  Future<Response> get(dynamic reference) {
    late final Uri uri = _resolveReference(reference);
    final request = Request('GET', uri);

    return authenticatedRequest2(request);
  }

  Future<StreamedResponse> streamedGet(dynamic reference) {
    late final Uri uri = _resolveReference(reference);
    final request = Request('GET', uri);

    return authenticatedRequest(request);
  }

  String _convertBody(dynamic body) {
    late final String result;

    if (body is DtoBase) {
      result = body.toJson();
    } else if (body is String) {
      result = body;
    } else {
      throw ArgumentError('Invalid body type: ${body.runtimeType}');
    }

    return result;
  }

  String _parseContentType(dynamic contentType) {
    return contentType?.toString() ?? _defaultContentType;
  }

  Future<Response> post(
    dynamic reference,
    dynamic body, {
    dynamic contentType,
  }) {
    final uri = _resolveReference(reference);
    body = _convertBody(body);
    final request = Request('POST', uri);

    request.contentType = _parseContentType(contentType);
    request.body = body;

    return authenticatedRequest2(request);
  }

  Future<Response> streamedPost(
    dynamic reference,
    Stream<List<int>> body, {
    dynamic contentType,
  }) {
    final uri = _resolveReference(reference);
    final request = StreamedRequest('POST', uri);

    request.contentType = _parseContentType(contentType);
    body.listen(
      (data) => request.sink.add(data),
      onDone: () => request.sink.close(),
    );

    return authenticatedRequest2(request);
  }

  Future<Response> put(
    dynamic reference,
    dynamic body, {
    ContentType? contentType,
  }) {
    final uri = _resolveReference(reference);
    body = _convertBody(body);
    final request = Request('PUT', uri);

    request.contentType = _parseContentType(contentType);
    request.body = body;

    return authenticatedRequest2(request);
  }

  Future<Response> streamedPut(
    dynamic reference,
    Stream<List<int>> body, {
    ContentType? contentType,
  }) {
    final uri = _resolveReference(reference);
    final request = StreamedRequest('PUT', uri);

    request.contentType = _parseContentType(contentType);
    body.listen(
      (data) => request.sink.add(data),
      onDone: () => request.sink.close(),
    );

    return authenticatedRequest2(request);
  }

  Future<Response> delete(dynamic reference) {
    final uri = _resolveReference(reference);
    final request = Request('DELETE', uri);

    return authenticatedRequest2(request);
  }
}
