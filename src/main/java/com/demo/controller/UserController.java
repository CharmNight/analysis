package com.demo.controller;


import com.demo.common.service.MetricsService;
import com.demo.common.annotation.ApiMonitor;
import com.demo.common.starage.MemoryStorage;
import com.demo.vo.UserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author night
 */
@RestController
public class UserController {


    @ApiMonitor
    @PostMapping("/register")
    public void register(@RequestBody UserVo user) {

        // ...
        try {
            int randomInt = new Random().nextInt(3);
            System.out.println(randomInt);
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/login")
    public UserVo login(String telephoto, String pwd) {
        // ..
        try {
            int randomInt = new Random().nextInt(3);
            System.out.println(randomInt);
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
