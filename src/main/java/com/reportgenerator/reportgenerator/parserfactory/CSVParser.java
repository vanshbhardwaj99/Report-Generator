package com.reportgenerator.reportgenerator.parserfactory;

import com.reportgenerator.reportgenerator.util.CSVRecordMapper;
import org.apache.commons.csv.CSVFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Component
public class CSVParser implements Parser{

    private static final Logger logger = LoggerFactory.getLogger(CSVParser.class);

    @Override
    public <T> List<T> parse(MultipartFile file, CSVRecordMapper<T> mapper) throws IOException {

        try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
        org.apache.commons.csv.CSVParser csvParser = new  org.apache.commons.csv.CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            List<T> data = new ArrayList<>();

            csvParser.forEach(csvRecord -> {
                T dataRecord = mapper.mapRecord(csvRecord);
                logger.info("csvRecord while parsing : {}", dataRecord);
                data.add(dataRecord);
            });

            csvParser.close();
            return data;

        }
    }
}
