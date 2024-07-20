package com.reportgenerator.reportgenerator.util;


import com.reportgenerator.reportgenerator.entity.InputData;
import com.reportgenerator.reportgenerator.entity.ReferenceData;

import java.math.BigDecimal;

public class DataMapper {

    public DataMapper(){
        throw new UnsupportedOperationException("Data Mapper cannot be instantiated");
    }

    public static CSVRecordMapper<InputData> getInputDataMapper(){
        return csvRecord -> {
            InputData dataRecord = new InputData();
            dataRecord.setField1(csvRecord.get("field1"));
            dataRecord.setField2(csvRecord.get("field2"));
            dataRecord.setField3(csvRecord.get("field3"));
            dataRecord.setField4(csvRecord.get("field4"));
            dataRecord.setField5(BigDecimal.valueOf(Double.parseDouble(csvRecord.get("field5"))));
            dataRecord.setRefkey1(csvRecord.get("refkey1"));
            dataRecord.setRefkey2(csvRecord.get("refkey2"));

            return dataRecord;
        };
    }

    public static CSVRecordMapper<ReferenceData> getReferenceDataMapper(){
        return csvRecord -> {
            ReferenceData dataRecord = new ReferenceData();
            dataRecord.setRefkey1(csvRecord.get("refkey1"));
            dataRecord.setRefdata1(csvRecord.get("refdata1"));
            dataRecord.setRefkey2(csvRecord.get("refkey2"));
            dataRecord.setRefdata2(csvRecord.get("refdata2"));
            dataRecord.setRefdata3(csvRecord.get("refdata3"));
            dataRecord.setRefdata4(BigDecimal.valueOf(Double.parseDouble(csvRecord.get("refdata4"))));

            return dataRecord;
        };
    }
}
