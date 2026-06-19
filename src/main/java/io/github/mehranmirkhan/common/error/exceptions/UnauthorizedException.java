package io.github.mehranmirkhan.common.error.exceptions;

import org.springframework.http.HttpStatus;
import io.github.mehranmirkhan.common.error.BusinessException;
import lombok.Builder;
import tools.jackson.databind.JsonNode;

public class UnauthorizedException extends BusinessException {
  @Builder
  public UnauthorizedException(String code, String message, JsonNode payload, Throwable cause) {
    super(HttpStatus.UNAUTHORIZED, code, message, payload, cause);
  }
}
