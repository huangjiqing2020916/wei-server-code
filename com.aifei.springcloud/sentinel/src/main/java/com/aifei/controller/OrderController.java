package com.aifei.controller;

import com.aifei.service.OrderQueryService;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static sun.security.x509.X509CertInfo.KEY;

@Controller
@RequestMapping("order")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderQueryService orderQueryService;

    @RequestMapping("/getOrder")
    @ResponseBody
    public String queryOrder1(@RequestParam("orderId") String orderId) {

        return orderQueryService.queryOrderInfo(orderId);
    }

    @PostConstruct
    public void initFlowQpsRule() {
        System.err.println("OrderController=>init");
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource("getOrderInfo");
        // QPS控制在2以内
        rule1.setCount(3);
        // QPS限流
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 限流实现方式一: 抛出异常的方式定义资源
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/getOrder1")
    @ResponseBody
    public String queryOrder2(@RequestParam("orderId") String orderId) {
        Entry entry = null;
        // 资源名
        String resourceName = "getOrderInfo";
        try {
            // entry可以理解成入口登记
            entry = SphU.entry(resourceName);
            // 被保护的逻辑, 这里为订单查询接口
            return orderQueryService.queryOrderInfo(orderId);
        } catch (BlockException blockException) {
            // 接口被限流的时候, 会进入到这里
            log.warn("---getOrder1接口被限流了---, exception: ", blockException);
            return "接口限流, 返回空";
        } finally {
            // SphU.entry(xxx) 需要与 entry.exit() 成对出现,否则会导致调用链记录异常
            if (entry != null) {
                entry.exit();
            }
        }
    }

    //注解

    /*
     *@param orderId
     *@return
     */
    @RequestMapping("/getOrder2")
    @ResponseBody
    public String queryOrder3(@RequestParam("orderId") String orderId) {
        return orderQueryService.queryOrderInfo2(orderId);
    }
}
