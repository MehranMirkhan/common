package io.github.mehranmirkhan.common.error;

import java.time.Instant;
import java.util.UUID;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CommonJpaExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<BusinessError> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {
    UUID errorId = UUID.randomUUID();
    String uri = ((ServletWebRequest) request).getRequest().getRequestURI();
    var error = BusinessError.builder().code("BAD_REQUEST").message(ex.getMessage())
        .timestamp(Instant.now()).uuid(errorId).requestUrl(uri).build();
    log.warn("ConstraintViolationException on {}, errorId: {}, message: {}", uri, errorId,
        ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}
