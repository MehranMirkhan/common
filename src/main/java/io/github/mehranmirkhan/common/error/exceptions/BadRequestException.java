package io.github.mehranmirkhan.common.error.exceptions;

import org.springframework.http.HttpStatus;
import io.github.mehranmirkhan.common.error.BusinessException;
import lombok.Builder;
import tools.jackson.databind.JsonNode;

public class BadRequestException extends BusinessException {
  @Builder
  public BadRequestException(String code, String message, JsonNode payload, Throwable cause) {
    super(HttpStatus.BAD_REQUEST, code, message, payload, cause);
  }
}
