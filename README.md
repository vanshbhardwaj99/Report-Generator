Report Generator

Report generator is a Java spring boot based microservice that can ingest CSV/JSON files, apply transformation rules, and generate reports. It has the following features:

- Ingest CSV/JSON files from specified paths.
- Apply configurable transformation rules.
- Generate output reports in CSV format.
- Schedule report generation using cron expressions.
- Trigger report generation via REST API.
- Designed to handle large files efficiently.
- Unit tests and logging for better maintainability.

It uses the following languages and framework:
  Java(21),
  Spring boot(v3.3.1),
  MongoDB(Embedded MongoDB implementation of de.flapdoodle),
  Gradle,
  JUnit and Mockito

The configuration can be changed in src/main/resources/application.properties.
Instance of mongoDb is started along with this application which can be accessed at url : mongodb://localhost:27017. The name of the DB is present in application.properties(By default it is report)

NOTE: The sample input and reference file is attached in src/main/resources/static. By default, the automatic scheduling runs every 30 seconds. This can be configured in application.properties file(cron expression).

This project mainly exposes 2 APIs:

1) API Design : http://127.0.0.1:8080/upload/ ,
HTTP: POST, 
Body: Array of CSV/JSON files,
Postman CURL : curl --location 'http://127.0.0.1:8080/upload/' \
--form 'file=@"/path/to/file"'

2) API Design : http://127.0.0.1:8080/reports/ ,
HTTP: GET, 
Postman CURL : curl --location 'http://127.0.0.1:8080/reports/generate'

