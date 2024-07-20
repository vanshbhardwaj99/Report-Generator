package com.reportgenerator.reportgenerator.util;

import org.apache.commons.csv.CSVRecord;

@FunctionalInterface
public interface CSVRecordMapper<T> {
    T mapRecord(CSVRecord csvRecord);
}
