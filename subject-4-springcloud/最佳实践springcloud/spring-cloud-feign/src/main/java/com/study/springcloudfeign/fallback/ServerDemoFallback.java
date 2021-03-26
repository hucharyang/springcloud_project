package com.study.springcloudfeign.fallback;

import com.study.springcloudfeign.service.ServerDemoSerivce;
import org.springframework.stereotype.Component;

@Component
public class ServerDemoFallback implements ServerDemoSerivce {


    @Override
    public String index() {
        return "无法调用，降级~~~";
    }
}
