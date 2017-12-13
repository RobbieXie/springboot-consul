package com.example.demo.service;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.health.model.HealthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author 谢天帝
 * @version v0.1 2017/12/13.
 */
@Service
public class ConsulService {


    ConsulClient consulClient;

    @Bean
    public ConsulClient consulClient(){
        this.consulClient = new ConsulClient(host,Integer.valueOf(port));
        return this.consulClient;
    }

    @Value("${consul.host}")
    private String host;

    @Value("${consul.port}")
    private String port;

    @Value("${spring.application.name}")
    private String clientName;

    public String registerService(String id){
        // register new service with associated health check
        NewService newService = new NewService();
        newService.setId(id);
        newService.setTags(Collections.singletonList("tiandi_v1"));
        newService.setName(clientName);
        newService.setAddress("http://10.60.38.176");
        newService.setPort(8080);

        NewService.Check serviceCheck = new NewService.Check();
        serviceCheck.setHttp("http://10.60.38.176:8080/health");
        serviceCheck.setInterval("5s");
        serviceCheck.setDeregisterCriticalServiceAfter("1m");
        newService.setCheck(serviceCheck);

        consulClient.agentServiceRegister(newService);
        return null;
    }

    public String deregisterService(String id){
        System.out.println("Deregister ------"+id);
        List<HealthService> response = consulClient.getHealthServices(clientName,false,null).getValue();
        System.out.println(response.size());
        for(HealthService service : response){
            //创建一个用来剔除无效实例的ConsulClient，连接到无效实例注册的agent
            ConsulClient clearClient = new ConsulClient("202.120.167.198",8500);
            service.getChecks().forEach(check -> {
                if (check.getServiceId().equals(id)){
                    Logger logger = Logger.getLogger(check.getServiceId());
                    logger.info("deregister:{}"+check.getServiceId());
                    clearClient.agentServiceDeregister(check.getServiceId());
                    System.out.println("Deregister success------"+id);
                }
            });
        }
        System.out.println("Deregister over ------"+id);
        return null;
    }
}
