package com.study.springcloudfeign.configuration;

import feign.Request;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeginTimeOutConfiguration {

    //前一个是连接超时，后一个是获得结果超时.
 public Request.Options options(){
     return new Request.Options(5000,10000);
 }
}
