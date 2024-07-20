package com.reportgenerator.reportgenerator.config;

import com.reportgenerator.reportgenerator.exception.GenericException;
import com.reportgenerator.reportgenerator.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.nio.file.Path;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);

    @Autowired
    private ReportService reportService;

    @Scheduled(cron = "${report.schedule}")
    public void scheduleReportGeneration(){
        try {
            logger.info("report scheduled");

            Path filePath = reportService.getReportDataAndExport();

            logger.info("report downloaded at path : {}", filePath);
        }
        catch(Exception e){
            logger.error("Something unexpected happened : {}", e);
            throw new GenericException(e);
        }
    }
}
