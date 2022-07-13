package com.night.metrics.annotation;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReporterEnum {
    CONSOLE("控制台打印", "console");

    private String name;
    private String key;

    public String getKey() {
        return key;
    }
}
