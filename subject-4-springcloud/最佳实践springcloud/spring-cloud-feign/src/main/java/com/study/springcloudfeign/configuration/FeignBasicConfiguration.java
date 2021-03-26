package com.study.springcloudfeign.configuration;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignBasicConfiguration {

    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        return  new BasicAuthRequestInterceptor("user","1111");
    }
}
