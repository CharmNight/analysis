package com.demo.common.output;


import com.demo.common.bean.RequestStat;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.Map;

public class ConsoleReporter implements ReporterInterface {

    @Override
    public void show(Map<String, RequestStat> stats) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(stats));
    }
}
