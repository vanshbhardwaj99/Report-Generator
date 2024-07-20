package com.reportgenerator.reportgenerator.error;

import lombok.Getter;

@Getter
public class Error {
    private int errorCode;
    private String message;
    private String customMessage;

    public Error(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Error(int errorCode, String message, String customMessage) {
        this.errorCode = errorCode;
        this.message = message;
        this.customMessage = customMessage;
    }
}