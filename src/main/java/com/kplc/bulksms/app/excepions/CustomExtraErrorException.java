package com.kplc.bulksms.app.excepions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomExtraErrorException extends RuntimeException {
    private HttpStatus status = null;
    private Object data = null;

    public CustomExtraErrorException(String message) {
        super(message);
    }

    public CustomExtraErrorException(HttpStatus status, String message) {
        this(message);
        this.status = status;
    }

    public CustomExtraErrorException(HttpStatus status, String message, Object data) {
        this(status, message);
        this.data = data;
    }
}
