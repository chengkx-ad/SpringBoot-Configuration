package com.chengkx.config;

import com.chengkx.entity.Car;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
// 标识webconfig是一个配置类  启用了Lite模式  每个返回的都是新创建的，多例模式  非代理模式
public class WebConfig {
    // 实现自定义转换器
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){

        return new WebMvcConfigurer() {
            @Override
            public void addFormatters(FormatterRegistry registry) {
                // 增加新的转换器  将String转换成Car
                // 使用匿名内部类的方式
                // 执行顺序，数据打过来以后，先进性数据转换，再将转换好的数据给Monster中的car
                //如果添加多个转换器，若转换的source和target相同，后面的转换器会覆盖前面的转换器
                registry.addConverter(new Converter<String, Car>() {
                    @Override
                    public Car convert(String source) {
                        if (!ObjectUtils.isEmpty(source)){
                            Car car = new Car();
                            String[] split = source.split(",");
                            car.setName(split[0]);
                            car.setPrice(Double.parseDouble(split[1]));
                            return car;
                        }
                        return null;
                    }
                });
            }
        };
    }

}
