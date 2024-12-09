package com.chengkx.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;

/**
 * 包含两个测试
 * 1、测试@RequestAttribute和@SessionAttribute
 * 2、测试复杂参数中的map   model  ServletResponse 举几个例子来看
 */

@Controller
public class RequestAttributeController {

    /**
     * 这里注意请求转发，此时这个controller类用的是@Controller注解，也配置了视图解析器，所以可以转发到path7资源
     * 但是如果使用的是@RestController注解，即便配置了视图解析器，也无法进行请求转发，会直接将forward:/path7以json形式显示在前端页面
     * @param request
     * @return
     */
    @GetMapping("/path6")
    public String setValue(HttpServletRequest request){
        request.setAttribute("name", "小成");
        return "forward:/path7";
    }

    @GetMapping("/path7")
    @ResponseBody
    public String getValue(@RequestAttribute("name") String username,
                           HttpServletRequest request){
        System.out.println("username--" + username);
        Object o = request.getAttribute("name");
        System.out.println("o" + o);
        return "success";
    }
    /**
     * 这个方法当作响应一个注册请求，传过来的数据由map和model接收
     * 针对复杂参数进行测试 map model
     * @return
     */
    @GetMapping("/map")
    public String Map(Map<String, Object> map,
                      Model model,
                      HttpServletResponse response){

        map.put("user", "chengkx");
        map.put("job", "java后端开发工程师");
        model.addAttribute("salary", 15000);

        // 进行重定向
        return "forward:/mapOk";
    }
    @GetMapping("/mapOk")
    @ResponseBody
    public String getMap(HttpServletRequest request){
        System.out.println("user--" + request.getAttribute("user"));
        System.out.println("job--" + request.getAttribute("job"));
        System.out.println("salary--" + request.getAttribute("salary"));

        return "Success";
    }

}
