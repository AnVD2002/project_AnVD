package com.example.AnVD_project.until.exception;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

public interface AbstractError {

    Long epochTime = System.currentTimeMillis();
    OffsetDateTime dateTime = OffsetDateTime.now();

    String getMessage();

    int getCode();

    HttpStatus getHttpStatus();

    Long getEpochTime();
    OffsetDateTime getDateTime();
}
