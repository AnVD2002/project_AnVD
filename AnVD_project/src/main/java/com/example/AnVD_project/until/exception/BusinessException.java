package com.example.AnVD_project.until.exception;

import com.example.AnVD_project.until.validate.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    private int code;
    private String message;
    private HttpStatus httpStatus;
    private AbstractError abstractError;
    private String extras;

    public BusinessException(AbstractError abstractError) {
        super(abstractError.getMessage(), null);
        this.abstractError = abstractError;
        this.code = abstractError.getCode();
        this.message = abstractError.getMessage();
        this.httpStatus = abstractError.getHttpStatus();
    }

    public BusinessException(AbstractError abstractError, String extras) {
        super(abstractError.getMessage(), null);
        this.abstractError = abstractError;
        this.code = abstractError.getCode();
        this.message = abstractError.getMessage();
        this.httpStatus = abstractError.getHttpStatus();
        this.extras = extras;
    }

    public BusinessException(AbstractError abstractError, String extras, String ...arg) {
        super(abstractError.getMessage(), null);
        this.abstractError = abstractError;
        this.code = abstractError.getCode();
        this.message = StringUtils.formatMessage(abstractError.getMessage(), arg);
        this.httpStatus = abstractError.getHttpStatus();
        this.extras = extras;
    }
}
