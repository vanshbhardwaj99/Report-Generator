package com.reportgenerator.reportgenerator.error;

public class ErrorCodesAndMessages {

    public ErrorCodesAndMessages(){
        throw new UnsupportedOperationException("ErrorCodesAndMessages cannot be instantiated");
    }

    public static final int GENERIC_EXCEPTION_CODE = 1;

    public static final String GENERIC_EXCEPTION_MESSAGE = "There was some error at our end";

    public static final int PARSING_EXCEPTION_CODE = 2;

    public static final String PARSING_EXCEPTION_MESSAGE = "There was error while parsing the file";

}
