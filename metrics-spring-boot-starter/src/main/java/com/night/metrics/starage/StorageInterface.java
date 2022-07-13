package com.night.metrics.starage;

import com.night.metrics.bean.RequestBean;

import java.util.List;
import java.util.Map;

/**
 * 存储类顶级接口
 *
 * @author night
 */
public interface StorageInterface {
    void doSaveRequest(RequestBean requestInfo);

    List<RequestBean> getRequestInfos(String apiName, Long startTime, Long endTime);

    Map<String, List<RequestBean>> getRequestInfos(Long startTime, Long endTime);
}
