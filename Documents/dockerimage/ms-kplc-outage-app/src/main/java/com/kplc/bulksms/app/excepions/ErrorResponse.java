package com.kplc.bulksms.app.excepions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {

    @JsonProperty("ResultCode")
    private int code;

    @JsonProperty("ResultStatus")
    private String status;

    @JsonProperty("ResultMessage")
    private String message;

    @JsonProperty("data")
    private Object data;


    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    public ErrorResponse(HttpStatus httpStatus, String message, Object data) {
        this(httpStatus,message);
        this.data = data;
    }
}
