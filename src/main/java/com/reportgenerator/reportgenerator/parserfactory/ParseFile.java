package com.reportgenerator.reportgenerator.parserfactory;

import com.reportgenerator.reportgenerator.entity.InputData;
import com.reportgenerator.reportgenerator.entity.ReferenceData;
import com.reportgenerator.reportgenerator.util.CSVRecordMapper;
import com.reportgenerator.reportgenerator.util.DataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class ParseFile {

    private static final Logger logger = LoggerFactory.getLogger(ParseFile.class);

    @Autowired
    private ParserFactory parserFactory;

    public <T> List<T> getParseData(MultipartFile file, Class<T> classToMap) throws IOException {
        String contentType = file.getContentType();
        logger.error("Content type : {}", contentType);

        Parser parser = parserFactory.getParser(file.getContentType());
        logger.info("parser : {}", parser);
        return switch (parser) {
            case JSONParser jsonParser -> jsonParser.parse(file, classToMap);
            case CSVParser csvParser -> csvParser.parse(file, getCSVRecordMapper(classToMap));
            default -> throw new IllegalArgumentException("Unsupported parser type: " + parser.getClass().getSimpleName());
        };
    }

    private <T> CSVRecordMapper<T> getCSVRecordMapper(Class<T> classToMap){
        return switch (classToMap) {
            case Class<?> c when c.equals(InputData.class) -> (CSVRecordMapper<T>)DataMapper.getInputDataMapper();
            case Class<?> c when c.equals(ReferenceData.class) -> (CSVRecordMapper<T>)DataMapper.getReferenceDataMapper();
            default -> (CSVRecordMapper<T>)DataMapper.getReferenceDataMapper();
        };
    }
}
