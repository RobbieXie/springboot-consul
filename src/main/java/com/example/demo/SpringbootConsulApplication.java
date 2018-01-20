package com.example.demo;

import com.example.demo.service.ConsulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PreDestroy;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringbootConsulApplication {

	@Value("${consul.instanceId}")
	private String instanceId;

	@Bean
	public String getInstanceId(){
		return instanceId;
	}

	@Autowired
	private ConsulService consulService;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootConsulApplication.class, args);
	}

	@PreDestroy
	public void beforeExit(){
		System.out.println("-----------------------------BEFORE  EXIT--------------------------------");
		consulService.deregisterService(instanceId);
	}
}
