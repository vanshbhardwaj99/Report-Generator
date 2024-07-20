package com.reportgenerator.reportgenerator;

import com.reportgenerator.reportgenerator.config.MongoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MongoConfig.class)
public class ReportgeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportgeneratorApplication.class, args);
	}

}
