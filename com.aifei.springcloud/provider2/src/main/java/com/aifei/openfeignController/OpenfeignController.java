package com.aifei.openfeignController;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenfeignController {

    @GetMapping("/payment/get/{id}")
    Long getPaymentById(@PathVariable("id") Long id) {
        System.err.println("B===>");
        return id;
    }

    @GetMapping("/payment/feign/timeout")
    String paymentFeignTimeOut() {
        System.err.println("B===>");
        return "seccess";
    }
}
