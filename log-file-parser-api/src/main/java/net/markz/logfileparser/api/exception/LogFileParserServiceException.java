package net.markz.logfileparser.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LogFileParserServiceException extends RuntimeException {
  private final HttpStatus httpStatus;

  public LogFileParserServiceException(final HttpStatus httpStatus, final String message) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
