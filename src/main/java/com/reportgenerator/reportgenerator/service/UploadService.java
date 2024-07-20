package com.reportgenerator.reportgenerator.service;

import com.reportgenerator.reportgenerator.parserfactory.Parser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    public void parseAndSave(MultipartFile[] file) throws IOException;

    public void parseAndSaveInputFile(MultipartFile file, String fileId) throws IOException;

    public void parseAndSaveReferenceFile(MultipartFile file, String fileId) throws IOException;

}
