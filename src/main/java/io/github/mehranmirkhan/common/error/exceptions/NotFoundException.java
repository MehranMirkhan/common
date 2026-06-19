package io.github.mehranmirkhan.common.error.exceptions;

import org.springframework.http.HttpStatus;
import io.github.mehranmirkhan.common.error.BusinessException;
import lombok.Builder;
import tools.jackson.databind.JsonNode;

public class NotFoundException extends BusinessException {
  @Builder
  public NotFoundException(String code, String message, JsonNode payload, Throwable cause) {
    super(HttpStatus.NOT_FOUND, code, message, payload, cause);
  }
}
