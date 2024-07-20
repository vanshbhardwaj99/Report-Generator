package com.reportgenerator.reportgenerator.util;

import com.reportgenerator.reportgenerator.entity.ReferenceData;

import java.math.BigDecimal;
import java.util.Objects;

public class TransformationRule {
    public static String transformOutfield1(String field1, String field2) {
        return field1 + field2;
    }

    public static String transformOutfield2(String refdata1) {
        return refdata1;
    }

    public static String transformOutfield3(ReferenceData referenceData) {
        if(Objects.nonNull(referenceData)) {
            return referenceData.getRefdata2() + referenceData.getRefdata3();
        }
        return "";
    }

    public static BigDecimal transformOutfield4(String field3, BigDecimal field5, BigDecimal refdata4) {
        return new BigDecimal(field3).multiply(field5.max(refdata4));
    }

    public static BigDecimal transformOutfield5(BigDecimal field5, BigDecimal refdata4) {
        return field5.max(refdata4);
    }
}