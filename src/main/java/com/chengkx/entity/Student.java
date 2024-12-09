package com.chengkx.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 学生实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    /**
     * 学生ID
     */
    private Long id;
    
    /**
     * 学生姓名
     */
    private String name;
    
    /**
     * 学生年龄
     */
    private Integer age;
    
    /**
     * 年级
     */
    private String grade;
    
    /**
     * 专业
     */
    private String major;
} 