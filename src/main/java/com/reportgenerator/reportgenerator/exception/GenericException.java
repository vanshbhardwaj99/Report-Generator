package com.reportgenerator.reportgenerator.exception;

import com.reportgenerator.reportgenerator.error.Error;
import com.reportgenerator.reportgenerator.error.ErrorCodesAndMessages;
import lombok.Getter;

@Getter
public class GenericException extends RuntimeException{

    private Error error;

    public GenericException(Exception ex){
        super(ex);
    }

    public GenericException(Exception ex, Error error){
        super(ex);
        this.error = error;
    }

    public static GenericException genericException(Exception ex){
        return new GenericException(ex,
                new Error(ErrorCodesAndMessages.GENERIC_EXCEPTION_CODE, ErrorCodesAndMessages.GENERIC_EXCEPTION_MESSAGE));
    }
}
