package com.demo.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "metrics.data")

public class DataInfo {
    private long periodInSeconds;

    private long durationInSeconds;

}
