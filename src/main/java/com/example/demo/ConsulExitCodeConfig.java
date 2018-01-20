package com.example.demo;

import com.example.demo.service.ConsulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 谢天帝
 * @version v0.1 2017/12/14.
 */
@Component
public class ConsulExitCodeConfig implements ExitCodeGenerator{

    @Resource
    private String instanceId;

    @Autowired
    private ConsulService consulService;

    @Override
    public int getExitCode() {
        System.out.println("----------------------Exception------------------------");
        System.out.println("InstanceId : " + instanceId);
        return 999;
    }
}
