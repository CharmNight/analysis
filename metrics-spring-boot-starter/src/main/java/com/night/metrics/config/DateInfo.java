package com.night.metrics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "metrics.data")

public class DateInfo {
    private long periodInSeconds;

    private long durationInSeconds;

}
