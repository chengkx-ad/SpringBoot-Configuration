package com.chengkx.controller;

import com.chengkx.bean.Furn;
import jakarta.annotation.Resource;
import jakarta.annotation.Resources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class helloController {

    @Value("${my.website}")
    private String website;

    @Resource
    private Furn furn;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        System.out.println(website);
        return "hello , cheng , welcome!!!";
    }

    @RequestMapping(value = "/furn")
    @ResponseBody
    public Furn furn(){
        return furn;
    }
}
