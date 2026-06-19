package io.github.mehranmirkhan.common.error.exceptions;

import org.springframework.http.HttpStatus;
import io.github.mehranmirkhan.common.error.BusinessException;
import lombok.Builder;
import tools.jackson.databind.JsonNode;

public class ConflictException extends BusinessException {
  @Builder
  public ConflictException(String code, String message, JsonNode payload, Throwable cause) {
    super(HttpStatus.CONFLICT, code, message, payload, cause);
  }
}
