package com.example.demo.controller;

import com.example.demo.entity.Result;
import com.example.demo.entity.Service;
import com.example.demo.service.ConsulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
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

    @GetMapping("/code")
    public String generateRandomCode(@RequestParam("id") String id ){
        //通过当前时间毫秒值和随机函数 获取一个随机数
        String token = id;

        //由于上述获取的随机数长度不定  所以要采用数据摘要类获取固定长度的随机数  MessageDigest
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(token.getBytes());

            //又由于md5数组如果转换为字符串基本上会是乱码  所以要用Base64Encoder 来进行转换  成字符串
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

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

    @GetMapping("/services")
    @ResponseBody
    public List<Service> getServices(){
        ArrayList<Service> services = new ArrayList<>();
        services.add(new Service("a", "/a", Collections.singletonList("input_a"), Arrays.asList("name","result")));
        services.add(new Service("b", "/b", Collections.singletonList("input_b"), Arrays.asList("name","result")));
        services.add(new Service("c", "/c", Collections.singletonList("input_c"), Arrays.asList("name","result")));
        return services;
    }

    @GetMapping("/a")
    public Result a() throws InterruptedException {
        Thread.sleep(3000);
        return new Result("a","A success");
    }

    @GetMapping("/b")
    public Result b(){
        return new Result("b","B success");
    }

    @GetMapping("/c")
    public Result c(){
        return new Result("c","C success");
    }
}
