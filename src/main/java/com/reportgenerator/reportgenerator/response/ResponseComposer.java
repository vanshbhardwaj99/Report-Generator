package com.reportgenerator.reportgenerator.response;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ResponseComposer {

    private static final Logger logger = LoggerFactory.getLogger(ResponseComposer.class);

    public ResponseComposer(){
        throw new UnsupportedOperationException("Response Composer cannot be instantiated");
    }

    public static <T> String createJsonStringFromResponse(List<T> response){
        logger.info("response : {} ", response);

        Gson gson = new Gson();
        String jsonOutput = gson.toJson(response);

        logger.info("response : {} ", jsonOutput);
        return jsonOutput.replace("\\\\", "\\");
    }
}
