package com.night.metrics.config;

import com.night.metrics.bean.RequestBean;
import com.night.metrics.bean.RequestStat;
import com.night.metrics.output.ReporterInterface;
import com.night.metrics.starage.StorageInterface;
import com.night.metrics.util.Aggregator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 主动推送配置
 * @author night
 */
public class PushConfiguration {
    private StorageInterface metricsStorage;
    private ReporterInterface metricsReporter;

    private ScheduledExecutorService executor;


    private PushConfiguration(){
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // 第1个代码逻辑：根据给定的时间区间，从数据库中拉取数据；
                long durationInMillis = durationInSeconds * 1000;
                long endTimeInMillis = System.currentTimeMillis();
                long startTimeInMillis = endTimeInMillis - durationInMillis;
                Map<String, List<RequestBean>> requestBeans =
                        metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
                Map<String, RequestStat> stats = new HashMap<>();
                for (Map.Entry<String, List<RequestBean>> entry : requestBeans.entrySet()) {
                    String apiName = entry.getKey();
                    List<RequestBean> requestBeansPerApi = entry.getValue();
                    // 第2个代码逻辑：根据原始数据，计算得到统计数据；
                    RequestStat requestStat = Aggregator.aggregate(requestBeansPerApi, durationInMillis);
                    stats.put(apiName, requestStat);
                }
                // 第3个代码逻辑：将统计数据显示到终端（命令行或邮件）；
                metricsReporter.show(stats);

            }
        }, 0, periodInSeconds, TimeUnit.SECONDS);
    }


    public static PushConfiguration getInstance(StorageInterface metricsStorage, ReporterInterface metricsReporter){
        PushConfiguration instance = InnerHolder.instance;
        if (instance.checkAttribute()){
            instance.buildAttribute(metricsReporter, metricsStorage);
        }
        return instance;
    }

    private void buildAttribute(ReporterInterface metricsReporter, StorageInterface metricsStorage) {
        this.metricsStorage = metricsStorage;
        this.metricsReporter = metricsReporter;
    }

    private synchronized boolean checkAttribute() {
        return this.metricsReporter == null || this.metricsStorage == null;
    }

    public static class InnerHolder {
        public static final PushConfiguration instance = new PushConfiguration();
    }


}
