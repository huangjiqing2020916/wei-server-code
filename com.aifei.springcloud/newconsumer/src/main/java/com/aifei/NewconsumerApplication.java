package com.aifei;

import com.aifei.model.Affair;
import com.aifei.model.Cloud;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("consumer")
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.aifei.mapper")
@EnableDiscoveryClient
public class NewconsumerApplication {

    @Autowired
    RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(NewconsumerApplication.class, args);
    }


      /*实例化RestTemplate

      @return*/

    @LoadBalanced
    @Bean
    // -------------------------------> 获取Rest客户端实例
    public RestTemplate rest() {
        return new RestTemplate();
    }


      /*Rest服务端使用RestTemplate发起http请求,然后得到数据返回给前端----gotoUser是为了区分getUser怕小伙伴晕头

      @param id
      @return*/

    @GetMapping("/liuhao")
    public String liuhao() {
        return "liuhao";
    }


    @GetMapping("/go/User")
    public Map<String, Object> getUser(@RequestParam Integer id) {
        Map<String, Object> data = new HashMap<>();

          /*小伙伴发现没有，地址居然是http://service-provider
          居然不是http://127.0.0.1:8082/
          因为他向注册中心注册了服务，服务名称service-provider,我们访问service-provider即可*/

        data = restTemplate.getForObject("http://service-provider/get/User?id=" + id, Map.class);
        return data;
    }

    @GetMapping("/goto/CloudData")
    public Map<String, Object> gotoCloudData() {
        Map<String, Object> data = new HashMap<>();

          /*小伙伴发现没有，地址居然是http://service-provider
          居然不是http://127.0.0.1:8082/
          因为他向注册中心注册了服务，服务名称service-provider,我们访问service-provider即可*/

        try {
            data = restTemplate.getForObject("http://service-provider/geto/CloudData", Map.class);
        } catch (Exception e) {
            throw new RuntimeException("###系统异常进行回滚###");
        }
        return data;
    }

    @GetMapping("/goto/AffairData")
    public Map gotoAffairData(@RequestParam String index) {
        Map<String, Object> data = new HashMap<>();

          /*小伙伴发现没有，地址居然是http://service-provider
          居然不是http://127.0.0.1:8082/
          因为他向注册中心注册了服务，服务名称service-provider,我们访问service-provider即可*/

        try {
            data = restTemplate.getForObject("http://service-provider/getAffairData", Map.class);
            System.err.println(data);
        } catch (Exception e) {
//            throw new RuntimeException("###系统异常进行回滚###");
            e.printStackTrace();
        }
        return data;
    }


    public String addCloudData() {
        Cloud cloud = new Cloud();
        cloud.setName("小八戒");
        cloud.setPwd("吃西瓜");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);//设置参数类型和编码

        HttpEntity<Cloud> request = new HttpEntity<>(cloud, headers);//包装到HttpEntity

        ResponseEntity<String> result = restTemplate.postForEntity("http://service-provider/addCloudData", request, String.class);//OK

        return result.getBody();
    }

    //    @PostMapping("/addAffairData")
    public String addAffairData() {
        Affair affair = new Affair();
        affair.setName("小猪");
        affair.setPwd("佩奇");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);//设置参数类型和编码

        HttpEntity<Affair> request = new HttpEntity<>(affair, headers);//包装到HttpEntity

        ResponseEntity<String> result = restTemplate.postForEntity("http://service-provider/addAffairData", request, String.class);//OK

        return result.getBody();
    }

    @PostMapping("/transcationTest")
    public String transcationTest() {
        Cloud cloud = new Cloud();
        cloud.setName("小八戒");
        cloud.setPwd("吃西瓜");
        Affair affair = new Affair();
        affair.setName("小猪");//测试事务长度超过二俩张表应当都不增加数据
        affair.setPwd("佩奇");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("c", cloud);
        map.put("a", affair);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);//设置参数类型和编码

        HttpEntity<Map<String, Object>> mapHttpEntity = new HttpEntity<>(map, headers);//包装到HttpEntity

        ResponseEntity<String> result = restTemplate.postForEntity("http://service-provider/transcationTest", mapHttpEntity, String.class);//OK
        return result.getBody();
    }


    @GetMapping("/getRequest")
    public String getRequest(@RequestBody Cloud cloud) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);//设置参数类型和编码

        Map<String, Object> map = new HashMap<>();//建立简单的String，String的map
        map.put("cloud", cloud);
        map.put("state", "1");
        map.put("messaces", "使用restTemplate的get请求进行传参数来调用server-provider服务器上的getRequest接口");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);//包装到HttpEntity

        ResponseEntity<Map> result = restTemplate.getForEntity("http://service-provider/getRequest", Map.class, map);
        System.err.println(result);

        return "get";
    }


    @PostMapping("/postRequest")
    public String postRequest(@RequestBody Cloud cloud) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);//设置参数类型和编码

        Map<String, Object> map = new HashMap<>();//建立简单的String，String的map
        map.put("cloud", cloud);
        map.put("state", "1");
        map.put("messaces", "使用restTemplate的post请求进行传参数来调用server-provider服务器上的postRequest接口");
        HttpEntity<Map<String, Object>> request1 = new HttpEntity<>(map, headers);//包装到HttpEntity
//postForEntity  -》 直接传递map参数
        ResponseEntity<Integer> respo1 = restTemplate.postForEntity("http://service-provider/postRequest", map, Integer.class);
//postForEntity  -》 传递httpentity参数
        ResponseEntity<Integer> respo2 = restTemplate.postForEntity("http://service-provider/postRequest", request1, Integer.class);//OK
//postForObject  -》 直接传递map参数
        Integer respo3 = restTemplate.postForObject("http://service-provider/postRequest", map, Integer.class);
//postForObject  -》 传递httpentity参数
        Integer respo4 = restTemplate.postForObject("http://service-provider/postRequest", request1, Integer.class);
        return "post";
    }


    @GetMapping("/wucanGet")
    public void wucanGet() {
        //请求地址
        String url = "http://service-provider/wucanGet";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        //获取响应的状态
        HttpStatus statusCode = forEntity.getStatusCode();
        //获取响应的header信息
        HttpHeaders headers = forEntity.getHeaders();
        //获取响应的body信息
        String body = forEntity.getBody();
        System.err.println(statusCode.value() + "<=========>" + statusCode.getReasonPhrase());
        System.err.println("头部信息====>" + headers);
        System.err.println("响应内容====>" + body);
        //getForObject函数实际上是对getForEntity函数的进一步封装，
        // 如果你只关注返回的消息体的内容，对其他信息都不关注，此时可以使用getForObject
        String result = restTemplate.getForObject(url, String.class);
        System.err.println("getForObject====>" + result);
    }

    @GetMapping("/doHttpGetTest")
    public void doHttpGetTest() throws UnsupportedEncodingException {
        // -------------------------------> 解决(响应数据可能)中文乱码 的问题
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        converterList.remove(1); // 移除原来的转换器
        // 设置字符编码为utf-8
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(1, converter); // 添加新的转换器(注:convert顺序错误会导致失败)
        restTemplate.setMessageConverters(converterList);

        // -------------------------------> (选择性设置)请求头信息
        // HttpHeaders实现了MultiValueMap接口
        HttpHeaders httpHeaders = new HttpHeaders();
        // 给请求header中添加一些数据
        httpHeaders.add("JustryDeng", "这是一个大帅哥!");

        // -------------------------------> 注:GET请求 创建HttpEntity时,请求体传入null即可
        // 请求体的类型任选即可;只要保证 请求体 的类型与HttpEntity类的泛型保持一致即可
        String httpBody = null;
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpBody, httpHeaders);

        // -------------------------------> URI
        StringBuffer paramsURL = new StringBuffer("http://service-provider/doHttpGetTest");
        // 字符数据最好encoding一下;这样一来，某些特殊字符才能传过去(如:flag的参数值就是“&”,不encoding的话,传不过去)
        paramsURL.append("?flag=" + URLEncoder.encode("&", "utf-8"));
        URI uri = URI.create(paramsURL.toString());

        //  -------------------------------> 执行请求并返回结果
        // 此处的泛型  对应 响应体数据   类型;即:这里指定响应体的数据装配为String
        ResponseEntity<String> response =
                restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        // -------------------------------> 响应信息
        //响应码,如:401、302、404、500、200等
        System.err.println(response.getStatusCodeValue());
        // 响应体
        if (response.hasBody()) {
            System.err.println(response.getBody());
        }

    }
}