package net.focik.Smartgaz.utils.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class HttpResponse {

    private int httpStatusCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss", timezone = "Europe/Warsaw")
    private Date timestamp;
    private HttpStatus httpStatus;
    private String reason;
    private String message;

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
        this.timestamp = new Date();
    }
}
