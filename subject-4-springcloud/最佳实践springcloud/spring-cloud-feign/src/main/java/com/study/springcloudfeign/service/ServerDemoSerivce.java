package com.study.springcloudfeign.service;

import com.study.springcloudfeign.fallback.HelloDemoFallback;
import com.study.springcloudfeign.fallback.ServerDemoFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name="server-demo",fallback = ServerDemoFallback.class)
public interface ServerDemoSerivce {

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index();
}
