package com.chengkx;

//项目启动

import com.chengkx.bean.Monster;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

// 表示这是一个springboot应用程序
@SpringBootApplication(scanBasePackages = "com.chengkx")
public class MainApp {
    public static void main(String[] args) {

        // 启动springboot应用程序/项目
        ConfigurableApplicationContext ioc =
                SpringApplication.run(MainApp.class, args);


        // 演示 输出组件 ====start
        String[] beanDefinitionNames = ioc.getBeanDefinitionNames();
        for (String string : beanDefinitionNames) {
            System.out.println(string);
        }
        // 演示 输出组件 ====end

        // 演示@Configuration注解 === start
        //Monster monster01 = ioc.getBean("monster01", Monster.class);
        //Monster monster02 = ioc.getBean("monster01", Monster.class);

        //System.out.println(monster01);
        //System.out.println(monster02);
        // 演示@Configuration注解 === end
//        System.out.println(ioc.containsBean("monster02"));
//
//        Monster monster = ioc.getBean("monster02", Monster.class);
//        System.out.println(monster);
    }
}