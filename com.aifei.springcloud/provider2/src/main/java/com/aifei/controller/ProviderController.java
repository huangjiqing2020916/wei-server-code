package com.aifei.controller;

import com.aifei.model.Affair;
import com.aifei.model.Cloud;
import com.aifei.service.AffairService;
import com.aifei.service.CloudService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProviderController {

    private static final Logger log = LoggerFactory.getLogger(ProviderController.class);

    @Autowired
    private CloudService cloudService;

    @Autowired
    private AffairService affairService;

    @GetMapping("/get/User")
    public Map getUser(@RequestParam Integer id,@RequestParam String name) {
        log.warn("Provider-ServerB");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", id);
        data.put("name", name);
        data.put("userName", "admin");
        data.put("from", "provider-B");
        return data;
    }

    @GetMapping("/geto/CloudData")
    public Map getCloudData() {
        log.warn("Provider-ServerB");
        Map<String, Object> data = new HashMap<String, Object>();
        Cloud cloud = cloudService.getById(1);
        data.put("messaces", "服务--->B");
        data.put("object", cloud);
        return data;
    }

    @GetMapping("/Hystrix")
    public String getHystrix(@RequestParam Integer id) {
        log.warn("Provider-ServerB");
        return "B==>" + id;
    }

    @GetMapping("/hello/time")
    public String hello(HttpServletRequest request) {
        log.warn("Provider-ServerB");
        return "hello, "
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                + ", extendtag ["
                + request.getHeader("extendtag")
                + "]";
    }


    //服务熔断
    @HystrixCommand(fallbackMethod = "getDefaultUser", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),//失败率达到60%后跳闸
    })
    @GetMapping("/getAffairData")
    public Map getAffairData(@RequestParam String index) {
        log.warn("Provider-ServerB");
        if ("A".equals(index)) {
            throw new RuntimeException("###熔断A服务###");
        }
        Map<String, Object> data = new HashMap<String, Object>();
        Affair affair = affairService.getById(1);
        data.put("messaces", "服务--->B");
        data.put("object", affair);
        data.put("index", index);
        return data;
    }


    public Map getDefaultUser(String index) {
        log.warn("Provider-ServerB");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("index", index);
        data.put("name", "熔断用户");
        data.put("messace", "B服务被熔断");
        return data;
    }


    @PostMapping("/addCloudData")
    public String addCloudData(@RequestBody Cloud cloud) {
        log.warn("Provider-ServerB");
        Boolean result = cloudService.save(cloud);
        return String.valueOf(result);
    }

    @PostMapping("/addAffairData")
    public String addAffairData(@RequestBody Affair affair) {
        log.warn("Provider-ServerB");
        Boolean result = affairService.save(affair);
        return String.valueOf(result);
    }

    @PostMapping("/transcationTest")
    public String transcationTest(@RequestBody Map<String, Object> map) {
        log.warn("Provider-ServerB");
        JSONObject cj = JSONObject.fromObject(map.get("c"));
        Cloud cloud = (Cloud) JSONObject.toBean(cj, Cloud.class);
        JSONObject aj = JSONObject.fromObject(map.get("a"));
        Affair affair = (Affair) JSONObject.toBean(aj, Affair.class);
        Boolean result = affairService.transcation(cloud, affair);
        return String.valueOf(result);
    }

    @GetMapping("/getRequest")
    public Map getRequest() {
        log.warn("Provider-ServerB");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("state", "getRequest");
        resultMap.put("messaces", "providerB收到请求返回数据");
        return resultMap;
    }

    @PostMapping("/postRequest")
    public Map postRequest(@RequestBody Map map) {
        log.warn("Provider-ServerB");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("cloud", map);
        resultMap.put("state", "postRequest");
        resultMap.put("messaces", "providerB收到请求返回数据");
        return resultMap;
    }

    @GetMapping("/wucanGet")
    public String wucanGet() {
        log.warn("Provider-ServerB");
        return "wucanGet";
    }

    @GetMapping("/doHttpGetTest")
    public String doHttpGetTest(HttpServletRequest request) throws UnsupportedEncodingException {
        log.warn("Provider-ServerB");
        System.err.println(request.getCharacterEncoding());
        String justryDeng = new String(request.getHeader("JustryDeng").getBytes("ISO-8859-1"), "utf-8");
        System.err.println(justryDeng);
        System.err.println("flag的值为:" + request.getParameter("flag"));
        return "我是一只小小小小鸟";
    }
}
