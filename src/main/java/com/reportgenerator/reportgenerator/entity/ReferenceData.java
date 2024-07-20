package com.reportgenerator.reportgenerator.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Document(collection = "reference_data")
@Data
public class ReferenceData {
    @Id
    private String id;
    @Indexed
    private String inputFileId;
    private String refkey1;
    private String refdata1;
    private String refkey2;
    private String refdata2;
    private String refdata3;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal refdata4;
}
