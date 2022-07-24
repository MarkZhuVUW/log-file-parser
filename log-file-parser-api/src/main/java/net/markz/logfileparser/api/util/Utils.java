package net.markz.logfileparser.api.util;

import lombok.extern.slf4j.Slf4j;
import net.markz.logfileparser.api.exception.Lazy;
import net.markz.logfileparser.api.exception.LogFileParserServiceException;
import org.springframework.http.ResponseEntity;

@Slf4j
public final record Utils() {

    /**
     * log all LogParsingException and return ResponseEntity with error code and body appended.
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> handleException(Lazy<T> callback) {
        try {
            return ResponseEntity.ok(callback.lazyDo());
        } catch (LogFileParserServiceException e) {
            // swallow LogParsingExceptions, log it for tracing and return a responseEntity with status code and body.
            log.error(e.toString());
            return ResponseEntity
                    .status(e.getHttpStatus())
                    .body(callback.lazyDo());
        }
    }

}

