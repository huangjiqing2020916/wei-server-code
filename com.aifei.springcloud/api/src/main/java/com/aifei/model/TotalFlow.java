package com.aifei.model;

import lombok.Data;

@Data
public class TotalFlow {
    private Integer totalFlowRateRecordNo;
    private Integer lineID;
    private String lineName;
    private Integer flowRateRecordNo;
    private String test_Time;
    private String customer;
    private String productModel;
    private String serialNo;
    private String useGasSeed;
    private String numberFurnaceHeads;
    private String furnaceNo;
    private String tester;
    private String proofreader;
}
