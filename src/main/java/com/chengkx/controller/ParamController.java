package com.chengkx.controller;

import com.chengkx.entity.Monster;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.PushBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ParamController {

    // 测试@PathVariable
    /**
     * 此时要传的参数为id和name，可以分开一个一个传
     * 也可以使用map，map会把所有的param全部接收，方便遍历
     * 记住：在传递参数这里，map只能定义为<String, String>,无论传递的是什么类型，都被当作字符串对待，
     * 如果想用Integer，拿出来单独进行转换
     * @param id
     * @param name
     * @param map
     * @return
     */
    @GetMapping("/path1/{id}/{name}")
    public String pathVariable(@PathVariable("id") Integer id,
                               @PathVariable("name") String name,
                               @PathVariable Map<String, String> map
                               ){
        System.out.println("id--" + id);
        System.out.println("name--" + name);
        System.out.println("map--" + map);
        // 输出结果
//        id--1
//        name--ckx
//        map--{name=ckx, id=001}
        return "success";
    }

    //获取请求头信息@RequestHeader
    //不区分大小写
    @GetMapping("/path2")
    public String getHeader(@RequestHeader("host") String host,
                            @RequestHeader Map<String, String> header
                            ){
        System.out.println("host--" + host);
        System.out.println("map--" + header);
        return "success";
    }

    // 获取参数@RequestParam
    // 注意：如果在表单中有一个对象多个值，例如这里的hobby，如果用map接收，它只能接收到第一个参数的值，也就是只有GP，没有DNF
    @GetMapping("/path3")
    public String getParam(@RequestParam("name") String name,
                           @RequestParam("age") Integer age,
                           @RequestParam("hobby") List<String> list,
                           @RequestParam Map<String, String> map
                           ){
        System.out.println("list--" + list);
        System.out.println("map--" + map);
        return "Success";
    }

    // 测试@CookieValue
    @GetMapping("/path4")
    public String getCookie(@CookieValue(value = "cookie_key", required = false) String cookieValue,
                            @CookieValue(value = "username", required = false)Cookie cookie){
        System.out.println(cookieValue);
        System.out.println(cookie);
        return "Success";
    }

    // 测试@RequestBody
    @PostMapping("/path5")
    public String getJson(@RequestBody String content){
        System.out.println(content);
        return "Success";
    }
    
    // 测试自定义对象参数
    @PostMapping("/savemonster")
    public String saveMonster(Monster monster){
        System.out.println(monster);
        return "Success";
    }

    
    
    

}
