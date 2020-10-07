package com.aifei.redisController;

import com.aifei.model.Cloud;
import com.aifei.response.Result;
import com.aifei.service.CloudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("redis")
public class RedisController {

    private static final Logger log = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private RedisTemplate<String, Object> template;

    @Autowired
    private HashOperations<String, String, Object> redisHash;// Redis Hash

    @Autowired
    private CloudService cloudService;

    @GetMapping("/setAll")
    public Result setAll() {
        ValueOperations<String, Object> redisObject = template.opsForValue();
        //增加String
        redisObject.set("str", "hello spring boot redis repeat");
        //增加int
        redisObject.set("num", 22);
        //增加boolean
        redisObject.set("flag", true);
        //增加Object
        Cloud cloud = new Cloud("redisNameRepeat", "rdisPwdRepeat", 22);
        redisObject.set("cloudObject", cloud);

        Map<String, Object> map = new HashMap<>();
        map.put("id", "idRepeat");
        map.put("name", "nameRepeat");
        map.put("amount", 12.34D);
        map.put("age", 18);
        redisHash.putAll("hashKey", map);//增加Map

        List<Object> list = new ArrayList<>();
        list.add("listRedis");
        list.add(cloud);
        list.add(map);
        redisObject.set("listRedis", list);//增加list

        return Result.SUCCESS();
    }

    @GetMapping("/getAll")
    public Result getAll() {
        ValueOperations<String, Object> redisObject = template.opsForValue();
        Map<String, Object> map = new HashMap<>();//拼装返回数据

        String str = (String) redisObject.get("str");//获取String
        map.put("str", str);
        int num = (int) redisObject.get("num");//获取int
        map.put("num", num);
        Boolean flag = (Boolean) redisObject.get("flag");//获取boolean
        map.put("flag", flag);
        Cloud cloud = (Cloud) redisObject.get("cloudObject");//获取Object
        map.put("cloudObject", cloud);

        String name = (String) redisHash.get("hashKey", "name");//获取hashKey（Map）中的key为name的value
        map.put("hashKey-name", name);
        Double amount = (Double) redisHash.get("hashKey", "amount");//获取hashKey（Map）中的key为amount的value
        map.put("hashKey-amount", amount);
        Map<String, Object> mapRedis = redisHash.entries("hashKey");//获取hashKey（Map）
        map.put("hashKey", mapRedis);
        Set<String> keySet = redisHash.keys("hashKey");//获取hashKey（Map）中的所有key
        map.put("hashKey=keys", keySet);
        List<Object> valueList = redisHash.values("hashKey");//获取hashKey（Map）中的所有value
        map.put("hashValue=values", valueList);

        List<Object> list = (List) redisObject.get("listRedis");

        map.put("list", list);
        return Result.SUCCESS(map);
    }

    //==========================================================================>redisDB
    @GetMapping("/getCloud")
    public Result getCloud() {
        ValueOperations<String, Object> redisObject = template.opsForValue();
        /*cloudService.selectCloudById("6");
        Cloud cloud = (Cloud) redisObject.get("cloud::6");*/

//        List<Cloud> cloudList = cloudService.getCloudAlls();
        List<Cloud> cloudList = (List<Cloud>) redisObject.get("cloud::all");

       /* cloudService.selectCloudByIdWithCondition("7",20);
        Cloud cloud = (Cloud) redisObject.get("cloud::7");*/

        /*cloudService.selectCloudByIdWithCondition("7",20);
        Cloud cloud = (Cloud) redisObject.get("cloud::7");*/

        /*cloudService.insertCloud(new Cloud("addName14","addPwd14",14));
        Cloud cloud = (Cloud) redisObject.get("cloud::14");*/

//        cloudService.deleteCloutById("13");
//        cloudService.deleteCloudByIdAndCleanCache("14");
        return Result.SUCCESS(cloudList);
    }


}
