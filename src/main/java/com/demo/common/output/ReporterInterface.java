package com.demo.common.output;

import com.demo.common.bean.RequestStat;

import java.util.Map;

/**
 * 输出类 顶级接口
 *
 * @author night
 */
public interface ReporterInterface {
    void show(Map<String, RequestStat> stats);
}
