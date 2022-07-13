package com.night.metrics.service;

import com.night.metrics.bean.RequestBean;
import com.night.metrics.starage.StorageInterface;

public class MetricsService {

    private StorageInterface MetricsStorage;

    public MetricsService(StorageInterface metricsStorage) {
        MetricsStorage = metricsStorage;
    }


    /**
     * 记录响应时间和时间戳
     *
     * @param apiName
     * @param timestamp
     */
    public void recordTimestamp(String apiName, Long responseTime, Long timestamp) {
        RequestBean requestBean = new RequestBean();
        requestBean.setApiName(apiName);
        requestBean.setResponseTime(responseTime);
        requestBean.setTimestamp(timestamp);

        MetricsStorage.doSaveRequest(requestBean);
    }


}
