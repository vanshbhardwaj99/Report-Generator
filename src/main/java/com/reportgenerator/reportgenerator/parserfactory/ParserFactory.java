package com.reportgenerator.reportgenerator.parserfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParserFactory {

    private static final String APPLICATION_JSON = "application/json";

    private static final String TEXT_CSV = "text/csv";

    private static final String APPLICATION_VND_MS_EXCEL = "application/vnd.ms-excel";

    @Autowired
    private JSONParser jsonParser;

    @Autowired
    private CSVParser csvParser;

    public Parser getParser(String contentType){
        return switch (contentType) {
            case APPLICATION_JSON -> jsonParser;
            case TEXT_CSV, APPLICATION_VND_MS_EXCEL -> csvParser;
            default -> throw new IllegalStateException("Unsupported file type: " + contentType);
        };
    }
}
