package com.aifei.init;

import com.aifei.config.SessionConfig;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class SessionInitializer extends AbstractHttpSessionApplicationInitializer {
    public SessionInitializer(){
        super(SessionConfig.class);
    }


}




