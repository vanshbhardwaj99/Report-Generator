package com.reportgenerator.reportgenerator.service;

import com.reportgenerator.reportgenerator.entity.InputData;
import com.reportgenerator.reportgenerator.entity.ReferenceData;
import com.reportgenerator.reportgenerator.exception.GenericException;
import com.reportgenerator.reportgenerator.parserfactory.ParseFile;
import com.reportgenerator.reportgenerator.repository.InputDataRepository;
import com.reportgenerator.reportgenerator.repository.ReferenceDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class UploadServiceImpl implements UploadService{

    private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    private static final String INPUT_FILE = "input";

    @Autowired
    private ParseFile parseFile;

    @Autowired
    private InputDataRepository inputDataRepository;

    @Autowired
    private ReferenceDataRepository referenceDataRepository;

    @Override
    public void parseAndSave(MultipartFile[] files) throws IOException {
        String fileId = UUID.randomUUID().toString();

        Stream.of(files).forEach(file -> {
            if(file.getOriginalFilename().startsWith(INPUT_FILE)){
                try {
                    parseAndSaveInputFile(file, fileId);
                } catch (IOException e) {
                    throw GenericException.genericException(e);
                }
            }
            else{
                try {
                    parseAndSaveReferenceFile(file, fileId);
                } catch (IOException e) {
                    throw GenericException.genericException(e);
                }
            }
        });
    }

    @Override
    public void parseAndSaveInputFile(MultipartFile file, String fileId) throws IOException {
        try {
            List<InputData> data = parseFile.getParseData(file, InputData.class);
            data.forEach(input -> input.setFileId(fileId));
            logger.info("input data is parsed successfully : {}", data);

            inputDataRepository.saveAll(data);
            logger.info("input data is stored successfully");
        } catch (IOException ex) {
            logger.error("failure in saving input file : {}", file.getOriginalFilename());
            throw GenericException.genericException(ex);
        }
    }

    @Override
    public void parseAndSaveReferenceFile(MultipartFile file, String fileId) throws IOException {
        try {
            List<ReferenceData> refData = parseFile.getParseData(file, ReferenceData.class);
            refData.forEach(ref -> ref.setInputFileId(fileId));
            logger.info("reference data is parsed successfully : {}", refData);

            referenceDataRepository.saveAll(refData);
            logger.info("reference data is stored successfully");

        } catch (IOException ex) {
            logger.error("failure in saving reference file : {}", file.getOriginalFilename());
            throw GenericException.genericException(ex);
        }
    }
}
