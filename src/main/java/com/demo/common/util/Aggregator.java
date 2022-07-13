package com.demo.common.util;

import com.demo.common.bean.RequestBean;
import com.demo.common.bean.RequestStat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Aggregator {
    public static RequestStat aggregate(List<RequestBean> requestBeans, long durationInMillis) {
        double maxRespTime = Double.MIN_VALUE;
        double minRespTime = Double.MAX_VALUE;
        double avgRespTime = -1;
        double p999RespTime = -1;
        double p99RespTime = -1;
        double sumRespTime = 0;
        long count = 0;
        for (RequestBean requestBean : requestBeans) {
            ++count;
            double respTime = requestBean.getResponseTime();
            if (maxRespTime < respTime) {
                maxRespTime = respTime;
            }
            if (minRespTime > respTime) {
                minRespTime = respTime;
            }
            sumRespTime += respTime;
        }
        if (count != 0) {
            avgRespTime = sumRespTime / count;
        }
        long tps = (long)(count / durationInMillis * 1000);
        Collections.sort(requestBeans, new Comparator<RequestBean>() {
            @Override
            public int compare(RequestBean o1, RequestBean o2) {
                double diff = o1.getResponseTime() - o2.getResponseTime();
                if (diff < 0.0) {
                    return -1;
                } else if (diff > 0.0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        int idx999 = (int)(count * 0.999);
        int idx99 = (int)(count * 0.99);
        if (count != 0) {
            p999RespTime = requestBeans.get(idx999).getResponseTime();
            p99RespTime = requestBeans.get(idx99).getResponseTime();
        }
        RequestStat requestStat = new RequestStat();
        requestStat.setMaxResponseTime(maxRespTime);
        requestStat.setMinResponseTime(minRespTime);
        requestStat.setAvgResponseTime(avgRespTime);
        requestStat.setP999ResponseTime(p999RespTime);
        requestStat.setP99ResponseTime(p99RespTime);
        requestStat.setCount(count);
        requestStat.setTps(tps);
        return requestStat;
    }
}

