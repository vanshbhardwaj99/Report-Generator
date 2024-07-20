package com.reportgenerator.reportgenerator.entrypoint;

import com.reportgenerator.reportgenerator.exception.GenericException;
import com.reportgenerator.reportgenerator.exception.ParsingException;
import com.reportgenerator.reportgenerator.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(UploadEntryPoint.class);

    @Autowired
    private UploadService uploadService;

    @PostMapping("/")
    public ResponseEntity<String> uploadFiles(@RequestParam("file") MultipartFile[] file) {
        try {
            logger.info("uploading...");
            uploadService.parseAndSave(file);
            return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded and processed successfully");
        }
        catch(ParsingException | GenericException ex){
            throw ex;
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing file: " + e.getMessage());
        }
    }
}
