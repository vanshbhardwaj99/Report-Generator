package com.reportgenerator.reportgenerator.service;

import com.reportgenerator.reportgenerator.entity.InputData;
import com.reportgenerator.reportgenerator.entity.ReferenceData;
import com.reportgenerator.reportgenerator.exception.GenericException;
import com.reportgenerator.reportgenerator.parserfactory.ParseFile;
import com.reportgenerator.reportgenerator.repository.InputDataRepository;
import com.reportgenerator.reportgenerator.repository.ReferenceDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UploadServiceImplTest {

    @Mock
    private ParseFile parseFile;

    @Mock
    private InputDataRepository inputDataRepository;

    @Mock
    private ReferenceDataRepository referenceDataRepository;

    @InjectMocks
    private UploadServiceImpl uploadService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testParseAndSaveInputFile_Success() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "input.txt", "text/plain", "file content".getBytes());
        String fileId = UUID.randomUUID().toString();

        List<InputData> inputDataList = List.of(new InputData());

        when(parseFile.getParseData(file, InputData.class)).thenReturn(inputDataList);

        uploadService.parseAndSaveInputFile(file, fileId);

        Mockito.verify(inputDataRepository, times(1)).saveAll(inputDataList);
    }

    @Test
    public void testParseAndSaveInputFile_Exception() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "input.txt", "text/plain", "file content".getBytes());
        String fileId = UUID.randomUUID().toString();

        doThrow(new IOException()).when(parseFile).getParseData(any(), any());

        assertThrows(GenericException.class, () -> uploadService.parseAndSaveInputFile(file, fileId));
    }

    @Test
    public void testParseAndSaveReferenceFile_Success() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "input.txt", "text/plain", "file content".getBytes());
        String fileId = UUID.randomUUID().toString();

        List<ReferenceData> referenceDataList = List.of(new ReferenceData());

        when(parseFile.getParseData(file, ReferenceData.class)).thenReturn(referenceDataList);

        uploadService.parseAndSaveReferenceFile(file, fileId);

        Mockito.verify(referenceDataRepository, times(1)).saveAll(referenceDataList);
    }

    @Test
    public void testParseAndSaveReferenceFile_Exception() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "input.txt", "text/plain", "file content".getBytes());
        String fileId = UUID.randomUUID().toString();

        doThrow(new IOException()).when(parseFile).getParseData(any(), any());

        assertThrows(GenericException.class, () -> uploadService.parseAndSaveReferenceFile(file, fileId));
    }
}
