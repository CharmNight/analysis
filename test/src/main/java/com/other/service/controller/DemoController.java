package com.other.service.controller;

import com.night.metrics.annotation.ApiMonitor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Night
 * @date 2022/7/13
 * @apiNote
 */
@RestController
public class DemoController {

    @ApiMonitor
    @GetMapping("/demo")
    public void demo(){
        System.out.println("demo");
    }
}
