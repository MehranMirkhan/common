package io.github.mehranmirkhan.common.error;

import org.springframework.http.HttpStatusCode;
import lombok.Getter;
import tools.jackson.databind.JsonNode;

@Getter
public class BusinessException extends RuntimeException {
  private String code;
  private JsonNode payload;
  private HttpStatusCode status;

  public BusinessException(HttpStatusCode status, String code, String message, JsonNode payload,
      Throwable cause) {
    super(message, cause);
    this.code = code;
    this.payload = payload;
    this.status = status;
  }
}
