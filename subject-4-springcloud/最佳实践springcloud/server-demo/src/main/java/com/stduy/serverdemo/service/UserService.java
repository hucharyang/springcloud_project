package com.stduy.serverdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Service;
import brave.Span;
import brave.Tracer;

@Service
public class UserService {

    @Autowired
    Tracer tracer;

    //这里代表依次数据库操作,通过span把它记录下来

    @NewSpan("userService-database-insert")//注解的方式记录下我们的操作
    public int insert(){
        System.out.println("插入数据成功!!");
        return 1;
    }

    public void test(){//此方法没有任何含义.就是来演示span的
         Span newSpan = this.tracer.nextSpan().name("userservice-test");
         try(Tracer.SpanInScope ws = this.tracer.withSpanInScope(newSpan.start())){
             newSpan.tag("tag-test","我是一个标签");
             System.out.println("test方法调用成功");
         }finally {
             newSpan.finish();
         }
     //span可以跨多个方法，只要没有finish，就可以通过tarcer.currentSpan()获取到当前线程内的一个span
    }
}
