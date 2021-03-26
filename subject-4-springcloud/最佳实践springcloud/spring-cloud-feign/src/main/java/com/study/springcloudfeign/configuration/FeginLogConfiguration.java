package com.study.springcloudfeign.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeginLogConfiguration {

    /**
     * none :不输出日志
     * Basic: 只输出请求方法的Url和响应状态码以及接口执行时间
     * Head : 输出完整的头信息
     * full : 全部日志
     * */
    @Bean
    Logger.Level feginLoggerLevel(){
        return Logger.Level.FULL;
    }
}
