package com.study.springcloudfeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value="helloserver",contextId="helloserver2")
public interface HelloDemoService2 {

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index();
}
