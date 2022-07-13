package com.night.metrics.starage;

import com.night.metrics.bean.RequestBean;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 内存存储
 *
 * @author night
 */
public class MemoryStorage implements StorageInterface {
    private Map<String, List<Double>> responseTimes = new HashMap<>();
    private Map<String, Map<Long, RequestBean>> timestamps = new HashMap<>();

    @Override
    public void doSaveRequest(RequestBean requestInfo) {
        if (requestInfo == null || !StringUtils.hasText(requestInfo.getApiName())) {
            return;
        }
        responseTimes.putIfAbsent(requestInfo.getApiName(), new ArrayList<>());
        responseTimes.get(requestInfo.getApiName()).add(requestInfo.getResponseTime());

        timestamps.putIfAbsent(requestInfo.getApiName(), new HashMap<>());
        timestamps.get(requestInfo.getApiName()).put((long) requestInfo.getResponseTime(), requestInfo);
    }


    @Override
    public List<RequestBean> getRequestInfos(String apiName, Long startTime, Long endTime){
        Map<Long, RequestBean> apiTimestamps = timestamps.get(apiName);

        List<RequestBean> res = new ArrayList<>();
        if(startTime == null){
            startTime = 0L;
        }
        if (endTime == null){
            endTime = Long.MAX_VALUE;
        }

        Iterator<Map.Entry<Long, RequestBean>> iterator = apiTimestamps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, RequestBean> next = iterator.next();
            Long timestamps = next.getKey();
            RequestBean requestBean = next.getValue();
            if (timestamps > startTime && timestamps < endTime) {
                res.add(requestBean);
            }
        }

        return res;
    }

    @Override
    public Map<String, List<RequestBean>>getRequestInfos(Long startTime, Long endTime){
        Map<String, List<RequestBean>> res = new HashMap<>();
        for (String apiName : timestamps.keySet()) {
            List<RequestBean> requestInfos = getRequestInfos(apiName, startTime, endTime);
            res.put(apiName, requestInfos);
        }
        return res;
    }
}
