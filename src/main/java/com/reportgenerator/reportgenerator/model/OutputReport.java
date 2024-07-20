package com.reportgenerator.reportgenerator.model;

import com.reportgenerator.reportgenerator.entity.InputData;
import com.reportgenerator.reportgenerator.entity.ReferenceData;
import com.reportgenerator.reportgenerator.util.TransformationRule;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class OutputReport {

    private String batchId;
    private String outfield1;
    private String outfield2;
    private String outfield3;
    private BigDecimal outfield4;
    private BigDecimal outfield5;

    public static OutputReport of(InputData inputData, List<ReferenceData> referenceData){

        BigDecimal refdata4 = getRefData4(inputData.getRefkey1(), inputData.getRefkey2(), referenceData);

        return new OutputReport(
                inputData.getFileId(),
                TransformationRule.transformOutfield1(inputData.getField1(), inputData.getField2()),
                TransformationRule.transformOutfield2(getField2Reference(inputData.getRefkey1(), referenceData)),
                TransformationRule.transformOutfield3(getField3Reference(inputData.getRefkey2(), referenceData)),
                TransformationRule.transformOutfield4(inputData.getField3(), inputData.getField5(), refdata4),
                TransformationRule.transformOutfield5(inputData.getField5(), refdata4)
        );
    }

    private static String getField2Reference(String refKey1, List<ReferenceData> referenceData){
        return referenceData.stream().filter(ref -> ref.getRefkey1().equals(refKey1))
                .map(ReferenceData::getRefdata1).findFirst().orElse("");
    }

    private static ReferenceData getField3Reference(String refKey2, List<ReferenceData> referenceData){
        return referenceData.stream().filter(ref -> ref.getRefkey2().equals(refKey2)).findFirst().orElse(null);
    }

    private static BigDecimal getRefData4(String refKey1, String refKey2, List<ReferenceData> referenceData){
        return referenceData.stream().filter(ref -> ref.getRefkey1().equals(refKey1) && ref.getRefkey2().equals(refKey2))
                .map(ReferenceData::getRefdata4).findFirst().orElse(BigDecimal.valueOf(0.0));
    }
}
