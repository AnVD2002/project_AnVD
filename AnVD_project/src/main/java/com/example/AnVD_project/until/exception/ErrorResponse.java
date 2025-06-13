package com.example.AnVD_project.until.exception;

import com.example.AnVD_project.until.validate.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements AbstractError{
    private int code;

    private String message;

    private HttpStatus httpStatus;
    private String extras;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public Long getEpochTime() {
        return epochTime;
    }

    @Override
    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public String toString() {
        return (
                "class ErrorResponse {\n" +
                        "    dateTime: " +
                        StringUtils.toIndentedString(dateTime) +
                        "\n" +
                        "    epochTimeMsec: " +
                        StringUtils.toIndentedString(epochTime) +
                        "\n" +
                        "    code: " +
                        StringUtils.toIndentedString(code) +
                        "\n" +
                        "    message: " +
                        StringUtils.toIndentedString(message) +
                        "\n" +
                        "    extras: " +
                        StringUtils.toIndentedString(extras) +
                        "\n" +
                        "}"
        );
    }
}
