extension DateTimeExt on DateTime {
  bool operator >(DateTime other) {
    return isAfter(other);
  }

  bool operator <(DateTime other) {
    return isBefore(other);
  }

  bool operator >=(DateTime other) {
    return isAfter(other) || isAtSameMomentAs(other);
  }

  bool operator <=(DateTime other) {
    return isBefore(other) || isAtSameMomentAs(other);
  }

  Duration operator -(DateTime other) {
    return difference(other);
  }

  DateTime operator +(Duration duration) {
    return add(duration);
  }
}
