package com.aifei.model;

import lombok.Data;

import java.util.Date;

@Data
public class FlowRecord {
    private Integer flowRecordNo;
    private Integer planNo;
    private Integer lineID;
    private String lineName;
    private Date date;
    private Integer shiftID;
    private String shiftName;
    private Integer addr;
    private String deviceNames;
    private Integer stationID;
    private String stationName;
    private String orderForm;
    private String productID;

}
