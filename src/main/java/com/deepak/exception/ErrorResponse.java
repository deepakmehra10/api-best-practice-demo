package com.deepak.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ErrorResponse {

    LocalDateTime timestamp;

    String message;

    String error;

    Integer status;

    String path;

}
