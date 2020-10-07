package com.aifei.tool;


/*
* 定义全局兜底方法
* */


import com.aifei.response.Result;
import com.alibaba.csp.sentinel.slots.block.BlockException;

public class MyHandler {

    public static Result handlerException(BlockException exception){
        return Result.FAIL("全局的兜底 ===========");
    }

}
