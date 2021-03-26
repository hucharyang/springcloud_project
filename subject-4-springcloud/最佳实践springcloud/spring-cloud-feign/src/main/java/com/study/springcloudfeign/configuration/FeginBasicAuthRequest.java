package com.study.springcloudfeign.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

//每次feign发起请求前都会执行这段代码。。
public class FeginBasicAuthRequest implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        //TODO 自定义逻辑
    }
}
