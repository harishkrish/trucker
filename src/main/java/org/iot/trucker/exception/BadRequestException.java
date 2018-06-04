package org.iot.trucker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception for bad request
 * @author Harish Krishnamurthi
 *
 */
@ResponseStatus(code= HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public BadRequestException(String message) {
        super(message);
    }
}
