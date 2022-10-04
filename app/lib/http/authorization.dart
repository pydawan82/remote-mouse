import 'dart:convert';
import 'package:http/http.dart';

abstract class Authentication {
  void addAuthorization(BaseRequest request);
  void updateAuthorization(BaseResponse response);
}

const _authHeader = 'Authorization';

class BasicAuthentication extends Authentication {
  final String username;
  final String password;

  BasicAuthentication({required this.username, required this.password});

  @override
  void addAuthorization(BaseRequest request) {
    final credentials = '$username:$password';
    final encodedCredentials = base64.encode(utf8.encode(credentials));

    request.headers[_authHeader] = 'Basic $encodedCredentials';
  }

  @override
  void updateAuthorization(BaseResponse response) {}
}

class CookieAndBasicAuthentication extends BasicAuthentication {
  static const _cookieHeader = 'Cookie';
  static const _setCookieHeader = 'Set-Cookie';
  static const _cookieName = 'JSESSIONID';

  String? _cookie;

  CookieAndBasicAuthentication({
    required String username,
    required String password,
    String? cookie,
  })  : _cookie = cookie,
        super(
          username: username,
          password: password,
        );

  @override
  void addAuthorization(BaseRequest request) {
    if (_cookie != null) {
      request.headers[_setCookieHeader] = _cookie!;
    }

    super.addAuthorization(request);
  }

  @override
  void updateAuthorization(BaseResponse response) {
    final cookies = response.headers[_cookieHeader]?.split('; ');
    final cookie = cookies?.firstWhere((s) => s.startsWith(_cookieName));
    if (cookie != null) {
      _cookie = cookie;
    }
  }
}
