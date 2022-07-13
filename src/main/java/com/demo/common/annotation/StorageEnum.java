package com.demo.common.annotation;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StorageEnum {
    MEMORY("内存存储", "memory");

    private String name;
    private String key;

    public String getKey() {
        return key;
    }
}
