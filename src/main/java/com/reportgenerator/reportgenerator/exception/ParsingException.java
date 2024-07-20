package com.reportgenerator.reportgenerator.exception;

import com.reportgenerator.reportgenerator.error.Error;
import lombok.Getter;

@Getter
public class ParsingException extends RuntimeException{

    private Error error;

    public ParsingException(Exception ex){
        super(ex);
    }

    public ParsingException(Exception ex, Error error){
        super(ex.getMessage());
        this.error = error;
    }
}
