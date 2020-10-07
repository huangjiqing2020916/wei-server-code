package com.aifei.model;

import lombok.Data;

import java.util.Date;

@Data
public class FlowRecordTest {
    private String productName;
    private String orderBarcode;
    private String productBarcode;
    private String barcode;
    private Date beginDateTime;
    private Integer testNo;
    private Integer teamNo;
    private Float DLY;
    private Float CHG;
    private Float BAL;
    private Float DET;
    private Float EXH;
    private Float testData;
    private Integer testUnit;
    private Integer testResult;
}
