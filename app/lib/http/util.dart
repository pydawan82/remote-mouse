import 'dart:convert';

import 'package:http/http.dart';

/// An extension for [BaseResponse]
extension ResponseCodes on BaseResponse {
  /// Returns true if status code is in the 1xx range.
  bool get isInfo => statusCode >= 100 && statusCode < 200;

  /// Returns true if the response is successful.
  /// A response is considered successful if the status code is in the 200-299
  /// range.
  bool get isOk => statusCode >= 200 && statusCode < 300;

  /// Returns true if the response is a redirection.
  bool get isRedirect => statusCode >= 300 && statusCode < 400;

  /// Returns true if the response is a client error.
  bool get isClientError => statusCode >= 400 && statusCode < 500;

  /// Returns true if the response is a server error.
  bool get isServerError => statusCode >= 500 && statusCode < 600;
}

/// An extension for [Response].
/// Provides getters to easily access the response body
/// as a [Map] or a [List] of [Map]s.
extension JsonResponse on Response {
  /// Returns the body of the response as a JSON object.
  ///
  /// The body is parsed using the [json] package.
  /// and expected to be valid JSON.
  dynamic get json => jsonDecode(body);

  /// Returns the body of the response as a [List].
  ///
  /// The body is expected to be a JSON array.
  List get jsonList => jsonDecode(body) as List;

  T dto<T>(T Function(Map<String, dynamic>) fromJson) => fromJson(json);

  /// Returns a list of DTOs from the response body.
  ///
  /// Use the [fromJson] function to map each object in the list.
  /// The body is expected to be a valid JSON array.
  List<T> dtoList<T>(T Function(Map<String, dynamic>) fromJson) {
    return jsonList.map((e) => fromJson(e)).toList();
  }
}

/// Extension methods for [BaseRequest].
///
/// Provides getters and setters for main headers.
extension RequestExtension on BaseRequest {
  /// Returns the content type of the request.
  ///
  /// It returns the value of the header 'Content-Type'.
  get contentType => headers['Content-Type'];

  /// Sets the content type of the request.
  ///
  /// It sets the value of the header 'Content-Type'.
  set contentType(value) => headers['Content-Type'] = value;

  /// Returns the accept of the request.
  ///
  /// It returns the value of the header 'Accept'.
  get accept => headers['Accept'];

  /// Sets the accept of the request.
  ///
  /// It sets the value of the header 'Accept'.
  set accept(value) => headers['Accept'] = value;

  /// Returns the authorization of the request.
  ///
  /// It returns the value of the header 'Authorization'.
  get acceptEncoding => headers['Accept-Encoding'];

  /// Sets the authorization of the request.
  ///
  /// It sets the value of the header 'Authorization'.
  set acceptEncoding(value) => headers['Accept-Encoding'] = value;

  /// Returns the authorization of the request.
  ///
  /// It returns the value of the header 'Authorization'.
  get authorization => headers['Authorization'];

  /// Sets the authorization of the request.
  ///
  /// It sets the value of the header 'Authorization'.
  set authorization(value) => headers['Authorization'] = value;
}
