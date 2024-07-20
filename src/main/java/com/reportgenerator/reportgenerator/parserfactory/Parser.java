package com.reportgenerator.reportgenerator.parserfactory;

import com.reportgenerator.reportgenerator.util.CSVRecordMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface Parser {

    default <T> List<T> parse(MultipartFile file, Class<T> classToMap) throws IOException{
        throw new UnsupportedOperationException("Not implemented");
    }

    default <T> List<T> parse(MultipartFile file, CSVRecordMapper<T> mapper) throws IOException{
        throw new UnsupportedOperationException("Not implemented");
    }

}
