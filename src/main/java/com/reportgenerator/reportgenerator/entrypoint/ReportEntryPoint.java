package com.reportgenerator.reportgenerator.entrypoint;

import com.reportgenerator.reportgenerator.response.ResponseComposer;
import com.reportgenerator.reportgenerator.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(ReportEntryPoint.class);

    @Autowired
    private ReportService reportService;

    @GetMapping("/generate")
    public ResponseEntity<String> generateReport() {

        logger.info("generating report");
        Path filePath = reportService.getReportDataAndExport();

        logger.info("report will be saved at path : {}", filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ filePath.getFileName());

        return ResponseEntity.created(filePath.toUri())
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(ResponseComposer.createJsonStringFromResponse(List.of(Map.of("message",
                        "The file has been downloaded to this path : " + filePath))));
    }

}
