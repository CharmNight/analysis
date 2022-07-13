package com.night.metrics.output;


import com.night.metrics.bean.RequestStat;
import com.google.gson.Gson;

import java.util.Map;

public class ConsoleReporter implements ReporterInterface {

    @Override
    public void show(Map<String, RequestStat> stats) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(stats));
    }
}
