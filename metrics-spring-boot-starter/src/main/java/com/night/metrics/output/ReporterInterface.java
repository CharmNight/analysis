package com.night.metrics.output;

import com.night.metrics.bean.RequestStat;

import java.util.Map;

/**
 * 输出类 顶级接口
 *
 * @author night
 */
public interface ReporterInterface {
    void show(Map<String, RequestStat> stats);
}
