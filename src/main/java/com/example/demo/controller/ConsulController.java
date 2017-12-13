package com.example.demo.controller;

import com.example.demo.service.ConsulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.logging.Logger;

/**
 * @author 谢天帝
 * @version v0.1 2017/12/13.
 */
@RestController
public class ConsulController {

    private Logger logger = Logger.getLogger(this.getClass().toString());

    @Resource
    private String instanceId;

    @Autowired
    private ConsulService consulService;

    @GetMapping("/deregister/{id}")
    public String deregisterService(@PathVariable String id){
        logger.info("deregister:" + id + "  " + instanceId);
        consulService.deregisterService(id);
        return "success";
    }

    @GetMapping("/register/{id}")
    public String registerService(@PathVariable String id){
        logger.info("register:"+ id + "  " + instanceId);
        consulService.registerService(id);
        return "success";
    }

    @GetMapping("/health")
    public String health(){
        return "success";
    }
}
