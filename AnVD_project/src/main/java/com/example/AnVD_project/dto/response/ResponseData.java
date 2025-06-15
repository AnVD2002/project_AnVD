package com.example.AnVD_project.dto.response;

import com.example.AnVD_project.enums.ResponseStatusCode;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ResponseData<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String time;

    private int code;

    private String message;

    @Setter
    private T data;

    public ResponseData() {
        this.code = 0;
        this.time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime().toString();
        this.message = "Success";
    }

    public ResponseData<T> success() {
        this.code = 200;
        this.message = "Success";
        return this;
    }

    public ResponseData<T> success(T data) {
        this.code = 200;
        this.data = data;
        return this;
    }

    public ResponseData<T> success(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
        return this;
    }

    public ResponseData<T> error(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public ResponseData<T> error(int code, String message, T data) {
        this.data = data;
        this.code = code;
        this.message = message;
        return this;
    }

    public ResponseData(ResponseStatusCode responseStatusCode) {
        this.code = responseStatusCode.getValue();
        this.time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime().toString();
        this.message = responseStatusCode.getDescription();
    }
}
