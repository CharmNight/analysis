package com.night.metrics.aspect;

import com.night.metrics.annotation.ApiMonitor;
import com.night.metrics.annotation.ReporterEnum;
import com.night.metrics.annotation.StorageEnum;
import com.night.metrics.output.ConsoleReporter;
import com.night.metrics.output.ReporterInterface;
import com.night.metrics.service.MetricsService;
import com.night.metrics.starage.MemoryStorage;
import com.night.metrics.starage.StorageInterface;
import com.night.metrics.util.SpringContextUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 切面逻辑
 * @author night
 */
@Aspect
public class ApiMonitorAspect {
    private StorageInterface metricsStorage = null;
    private MetricsService metricsService = null;

    @Before("@annotation(apiMonitor)")
    public void before(JoinPoint joinPoint, ApiMonitor apiMonitor){

        if (apiMonitor.storage().equals(StorageEnum.MEMORY)) {
            metricsStorage = SpringContextUtils.getBean(MemoryStorage.class);
        }

        // 记录
        metricsService = new MetricsService(metricsStorage);

    }

    @Around("@annotation(apiMonitor)")
    public Object around(ProceedingJoinPoint joinPoint, ApiMonitor apiMonitor) throws Throwable {

        String apiName = joinPoint.getSignature().getName();
        long startTimestamp = System.currentTimeMillis();

        Object res = null;
        try {
            res = joinPoint.proceed();
        }catch (Exception e){
            throw e;
        }

        long respTime = System.currentTimeMillis()-startTimestamp;
        metricsService.recordTimestamp(apiName, startTimestamp, respTime);

        return res;
    }
}
