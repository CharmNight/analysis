package com.night.metrics.bean;

import lombok.Data;

@Data
public class RequestBean {
    private String apiName;

    private double responseTime;

    private long timestamp;
}
