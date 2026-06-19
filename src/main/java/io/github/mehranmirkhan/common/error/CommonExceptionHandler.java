package io.github.mehranmirkhan.common.error;

import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CommonExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<BusinessError> handleBusinessException(BusinessException ex,
      WebRequest request) {
    UUID errorId = UUID.randomUUID();
    String uri = getRequestUri(request);
    var error = BusinessError.builder().code(ex.getCode()).message(ex.getMessage())
        .timestamp(Instant.now()).uuid(errorId).requestUrl(uri).build();
    log.warn("BusinessException on {}, errorId: {}, code: {}, message: {}", uri, errorId,
        ex.getCode(), ex.getMessage());
    return ResponseEntity.status(ex.getStatus()).body(error);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class,
      MissingServletRequestParameterException.class, HttpRequestMethodNotSupportedException.class,
      HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class,
      ServletRequestBindingException.class, ConversionNotSupportedException.class,
      TypeMismatchException.class, MissingPathVariableException.class,
      NoHandlerFoundException.class, IllegalArgumentException.class})
  public ResponseEntity<BusinessError> handleBadRequestException(Exception ex, WebRequest request) {
    UUID errorId = UUID.randomUUID();
    String uri = getRequestUri(request);
    var error = BusinessError.builder().code("BAD_REQUEST").message(ex.getMessage())
        .timestamp(Instant.now()).uuid(errorId).requestUrl(uri).build();
    log.warn("BadRequestException on {}, errorId: {}, message: {}", uri, errorId, ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<BusinessError> handleUnknownException(Exception ex, WebRequest request) {
    UUID errorId = UUID.randomUUID();
    String uri = getRequestUri(request);
    var error = BusinessError.builder().code("INTERNAL_SERVER_ERROR").message(ex.getMessage())
        .timestamp(Instant.now()).uuid(errorId).requestUrl(uri).build();
    log.error("Exception on {}, errorId: {}, message: {}", uri, errorId, ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  private String getRequestUri(WebRequest request) {
    return getRequestUri(((ServletWebRequest) request).getRequest());
  }

  private String getRequestUri(HttpServletRequest request) {
    return request.getRequestURI();
  }
}
