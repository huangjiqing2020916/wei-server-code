package com.aifei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@SpringBootApplication
public class SessionRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionRedisApplication.class, args);
    }

    @Value("${server.port}")
    private Integer projectPort;

    @RequestMapping("createSession")
    public String createSession(HttpSession session, String name,String pwd) {
        session.setAttribute("name", name);
        return "1当前项目端口：" + projectPort + "\n当前sessionId：" + session.getId() + "\n在Session中存入成功！";
    }

    @RequestMapping("getSession")
    public String getSession(HttpSession session) {
        return "1当前项目端口：" + projectPort + "\n当前sessionId：" + session.getId() + "\n获取的姓名：" + session.getAttribute("name");
    }

    @RequestMapping("/destroy")
    public String destroy(HttpSession session){
        String name = session.getAttribute("name").toString();
//        session.invalidate();

        return "1销毁"+name+"成功！！！";
    }

}
