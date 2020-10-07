package com.aifei.model;

import lombok.Data;

@Data
public class totalFlowData {
    private Float lowerLimitRatedThermalLoad;
    private Float setTestTim;
    private Float dryDesignGasRelativeDensity;
    private Float lowCalorificValueDesignGas;
    private Float gasFlow;
    private Float gasPressureFlowmeter;
    private Float gasPressureBeforeStove;
}
