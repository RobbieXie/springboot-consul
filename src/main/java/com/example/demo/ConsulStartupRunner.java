package com.example.demo;

import com.example.demo.service.ConsulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 谢天帝
 * @version v0.1 2017/12/13.
 */
@Component
public class ConsulStartupRunner implements CommandLineRunner {

    @Resource
    private String instanceId;

    @Autowired
    private ConsulService consulService;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Consul start up........"+instanceId);
        consulService.registerService(instanceId);
    }
}
