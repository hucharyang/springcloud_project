package com.study.customer;

import com.study.customer.event.MyEvent;
import com.study.customer.stream.StreamClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;

@SpringBootApplication
@RestController
@RibbonClients(
        @RibbonClient(value = "hello-server")
)
@EnableBinding(StreamClient.class)
public class CustomerDemoApplication {

    public static void main(String[] args) {
       ConfigurableApplicationContext context=  SpringApplication.run(CustomerDemoApplication.class, args);

       BusProperties busProperties = context.getBean(BusProperties.class);
       context.publishEvent(new MyEvent("这是springcloud-bus",busProperties.getId()));
    }

    //自定义发布事件
  public static class MyApplicationEvent extends ApplicationEvent{

      public MyApplicationEvent(String msg) {
          super(msg);
      }
  }


  @Configuration
  public static  class EventConfiguration{

        @EventListener
        public void onEvent(MyApplicationEvent event){
            System.out.println("监听到事件"+event);
        }
  }

    @Bean
    @LoadBalanced
    public RestTemplate template(){
        return new RestTemplate();
    }

//    @Bean
////    public IRule ribbonRule(){
////
////          //自定义规则
////        return new RandomRule();
////    }

}
