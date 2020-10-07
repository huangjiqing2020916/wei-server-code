package com.aifei.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoodsQueryService {

    private static final Logger log = LoggerFactory.getLogger(GoodsQueryService.class);

    private static final String KEY = "queryGoodsInfo2";

    /**
     * 模拟商品查询接口
     *
     * @param spuId
     * @return
     */
    @SentinelResource(value = KEY, blockHandler = "blockHandlerMethod", fallback = "queryGoodsInfoFallback")
    public String queryGoodsInfo(String spuId) {

        // 模拟调用服务出现异常
        if ("0".equals(spuId)) {
            throw new RuntimeException();
        }

        return "query goodsinfo success, " + spuId;
    }

    public String blockHandlerMethod(String spuId, BlockException e) {
        log.warn("queryGoodsInfo error, blockHandlerMethod res:=>限流：", e.toString());
        return "queryGoodsInfo error, blockHandlerMethod res:=>限流：" + spuId;
    }

    public String queryGoodsInfoFallback(String spuId, Throwable e) {
        log.warn("queryGoodsInfo error, return fallback res=>熔断：", e.toString());
        return "queryGoodsInfo error, return fallback res=>熔断：" + spuId;
    }

//    @PostConstruct
    public void initDegradeRule() {
        System.err.println("GoodsQueryService=>init");
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(KEY);
        // 80s内调用接口出现异常次数超过5的时候, 进行熔断
        rule.setCount(5);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        rule.setTimeWindow(80);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }
}
