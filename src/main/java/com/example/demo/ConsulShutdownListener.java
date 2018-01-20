package com.example.demo;

import com.example.demo.service.ConsulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 谢天帝
 * @version v0.1 2017/12/14.
 */
@Component
public class ConsulShutdownListener implements ApplicationListener<ApplicationFailedEvent>{

    @Resource
    private String instanceId;

    @Autowired
    private ConsulService consulService;

    @Override
    public void onApplicationEvent(ApplicationFailedEvent applicationFailedEvent) {
        Throwable throwable = applicationFailedEvent.getException();
        handleThrowable(throwable);
    }

    /*处理异常*/
    private void handleThrowable(Throwable throwable) {
        System.out.println("----------------------Exception------------------------");
        System.out.println(throwable.getMessage());
        consulService.deregisterService(instanceId);
    }
}
