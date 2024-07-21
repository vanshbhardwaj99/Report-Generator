package com.reportgenerator.reportgenerator.entrypoint;

import com.reportgenerator.reportgenerator.exception.GenericException;
import com.reportgenerator.reportgenerator.exception.ParsingException;
import com.reportgenerator.reportgenerator.service.UploadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UploadEntryPointTest {

    @Mock
    private UploadService uploadService;

    @InjectMocks
    private UploadEntryPoint uploadEntryPoint;

    @Test
    public void testUploadFiles_Success() throws IOException {
        MultipartFile[] files = {mockFile()};

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CREATED)
                .body("[{\"message\":\"File uploaded and processed successfully\"}]");

        ResponseEntity<String> response = uploadEntryPoint.uploadFiles(files);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());

        verify(uploadService, times(1)).parseAndSave(files);
    }

    @Test
    public void testUploadFilesThrowsParsingException() throws IOException {
        MultipartFile[] files = {mockFile()};

        ParsingException parsingException = new ParsingException(new Exception("Parsing error"));
        doThrow(parsingException).when(uploadService).parseAndSave(files);

        assertThrows(ParsingException.class, () -> uploadEntryPoint.uploadFiles(files));

        verify(uploadService, times(1)).parseAndSave(files);
    }

    @Test
    public void testUploadFilesThrowsGenericException() throws IOException {
        MultipartFile[] files = {mockFile()};

        GenericException genericException = GenericException.genericException(new Exception("Generic error"));
        doThrow(genericException).when(uploadService).parseAndSave(files);

        assertThrows(GenericException.class, () -> uploadEntryPoint.uploadFiles(files));

        verify(uploadService, times(1)).parseAndSave(files);
    }

    private static MultipartFile mockFile() {
        return mock(MultipartFile.class);
    }
}
