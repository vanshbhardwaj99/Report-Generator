package com.reportgenerator.reportgenerator.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Data
@Document(collection = "input_data")
public class InputData {
    @Id
    @Indexed
    private String id;
    private String fileId;
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal field5;
    private String refkey1;
    private String refkey2;
}
