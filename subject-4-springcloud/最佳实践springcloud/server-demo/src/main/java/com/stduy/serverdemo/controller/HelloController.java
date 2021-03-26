package com.stduy.serverdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("index")
    public String index(){

        return "欢迎来到链路追踪sleuth的世界";
    }
}
