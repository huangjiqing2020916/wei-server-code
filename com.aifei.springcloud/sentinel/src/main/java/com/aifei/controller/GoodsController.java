package com.aifei.controller;

import com.aifei.service.GoodsQueryService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


// 测试类
@Controller
@RequestMapping("goods")
class GoodsController {

    private static final Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsQueryService goodsQueryService;

    @RequestMapping("/queryGoodsInfo")
    @ResponseBody
    public String queryGoodsInfo(@RequestParam("spuId") String spuId) {
        String res = goodsQueryService.queryGoodsInfo(spuId);
        log.warn("SUCCESS   ："+res);
        return res;
    }

    /**
     * 代码不加任何限流 熔断
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    @SentinelResource("test")
    public String test() {
        return "test";
    }
}
