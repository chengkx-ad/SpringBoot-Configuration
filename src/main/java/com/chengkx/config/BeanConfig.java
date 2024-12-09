package com.chengkx.config;

import com.chengkx.bean.Monster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//@Import(Monster.class)
//@Configuration(proxyBeanMethods = true)  单例  多例
// 将beans.xml文件导入配置类
@Configuration
@ImportResource("classpath:beans.xml")
public class BeanConfig {

//    //若不加(name = "monster1")那么该bean的id就是方法名，但是也可以通过name指定
    @Bean
    //@ConditionalOnBean(name = "monster_ckx")
    // 必须有一个叫monster_ckx的bean，才会注入monster01  注意此时只约束bean的名字  类型不作约束
    // conditional有很多这样的注解  分别有不同的作用
    // 这个注解也可以放在类上不只是方法上  放在类上 表示这个类里的所有方法都会受到约束
    //@Bean(name = "monster1")
    public Monster monster01(){
        return new Monster(100, "小成", "醉拳", 25);
    }

    

}
