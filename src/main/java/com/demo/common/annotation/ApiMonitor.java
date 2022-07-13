package com.demo.common.annotation;


import com.demo.common.starage.MemoryStorage;
import com.demo.common.starage.StorageInterface;

import java.lang.annotation.*;

/**
 * 采集执行时间
 * @author night
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiMonitor {
    /**
     * 输出方式
     * @return
     */
    ReporterEnum reporter() default ReporterEnum.CONSOLE;
    /**
     * 存储方式
     *
     * @return
     */
    StorageEnum storage() default StorageEnum.MEMORY;
}
