package com.app.tienda.exception;

import java.util.List;

public class BadRequestException extends RuntimeException {
  private final List<String> errors;

  public ValidationException(List<String> errors) {
    this.errors = errors;
  }

  public List<String> getErrors() {
    return errors;
  }
}
