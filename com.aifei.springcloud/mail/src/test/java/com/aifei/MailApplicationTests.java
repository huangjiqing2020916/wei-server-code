package com.aifei;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailApplicationTests {

    @Test
    void contextLoads() {
        MailAccount account = new MailAccount();
        account.setHost("smtp.qq.com");
        account.setPort(25);
        account.setAuth(true);
        account.setFrom("3533418157@qq.com");
        account.setUser("3533418157@qq.com");
        account.setPass("iqmibyllvkbgcjgj"); //密码
        MailUtil.send(account, CollUtil.newArrayList("3533418157@qq.com"), "艾菲亲测", "邮件来自艾菲com.aifei.springcloudProject亲测！", false);
    }
}
