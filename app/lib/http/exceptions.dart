/// Represents any http exception.
class HttpException implements Exception {
  /// The status code of the http response.
  final int statusCode;

  /// The message linked to the error of the http response.
  final String message;

  HttpException(this.statusCode, this.message);

  @override
  String toString() => 'HttpException: $statusCode: $message';
}

/// 400: ClientErrorException
class ClientErrorException extends HttpException {
  ClientErrorException(int statusCode, String message)
      : super(statusCode, message);
}

/// 401: UnauthorizedException
class UnauthorizedException extends HttpException {
  UnauthorizedException(int statusCode, String message)
      : super(statusCode, message);
}

/// 403: ForbiddenException
class ForbiddenException extends HttpException {
  ForbiddenException(int statusCode, String message)
      : super(statusCode, message);
}

/// 404: NotFoundException
class NotFoundException extends HttpException {
  NotFoundException(int statusCode, String message)
      : super(statusCode, message);
}

/// 409: ConflictException
class ConflictException extends HttpException {
  ConflictException(int statusCode, String message)
      : super(statusCode, message);
}

/// 500: InternalServerErrorException
class InternalServerErrorException extends HttpException {
  InternalServerErrorException(int statusCode, String message)
      : super(statusCode, message);
}

/// Thrown when the body of the request is not matching the expected format.
class ParseException implements Exception {}
