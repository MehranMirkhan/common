package io.github.mehranmirkhan.common.error.exceptions;

import org.springframework.http.HttpStatus;
import io.github.mehranmirkhan.common.error.BusinessException;
import lombok.Builder;
import tools.jackson.databind.JsonNode;

public class ForbiddenException extends BusinessException {
  @Builder
  public ForbiddenException(String code, String message, JsonNode payload, Throwable cause) {
    super(HttpStatus.FORBIDDEN, code, message, payload, cause);
  }
}
