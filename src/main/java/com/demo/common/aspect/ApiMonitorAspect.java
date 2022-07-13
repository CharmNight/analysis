package com.demo.common.aspect;

import com.demo.common.annotation.ReporterEnum;
import com.demo.common.annotation.StorageEnum;
//import com.demo.common.config.PushConfiguration;
import com.demo.common.output.ConsoleReporter;
import com.demo.common.output.ReporterInterface;
import com.demo.common.service.MetricsService;
import com.demo.common.annotation.ApiMonitor;
import com.demo.common.starage.MemoryStorage;
import com.demo.common.starage.StorageInterface;
import com.demo.common.util.SpringContextUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 切面逻辑
 * @author night
 */
@Aspect
@Component
public class ApiMonitorAspect {
    private StorageInterface metricsStorage = null;
    private ReporterInterface metricsReporter = null;
//    private PushConfiguration pushConfiguration = null;
    private MetricsService metricsService = null;

    @Before("@annotation(apiMonitor)")
    public void after(JoinPoint joinPoint, ApiMonitor apiMonitor){

        if (apiMonitor.storage().equals(StorageEnum.MEMORY)) {
            metricsStorage = SpringContextUtils.getBean(MemoryStorage.class);
        }
        if (apiMonitor.reporter().equals(ReporterEnum.CONSOLE)) {
            metricsReporter = SpringContextUtils.getBean(ConsoleReporter.class);
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
