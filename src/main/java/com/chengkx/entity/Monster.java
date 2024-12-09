package com.chengkx.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


@ConfigurationProperties(value = "monster")
@Component
@Data
@ToString
public class Monster {

    private Integer id;
    private String name;
    private Integer age;
    private Boolean isMarried;
    private Date birth;
    private Car car;
    private String[] skill;
    private List<String> hobby;
    private Map<String, Object> wife;
    private Set<Double> salaries;
    private Map<String, List<Car>> cars;

}
