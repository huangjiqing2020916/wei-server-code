package com.aifei.model;

import lombok.Data;

@Data
public class TotalFlowGas {
    private Float testAirConsumption;
    private Float gasTemperature;
    private Float gasHumidity;
    private Float measuredHeatLoad;
    private Float accuracyRatedThermalLoad;
    private Float equivalentHeatLoad;
    private Float testResult;
}
