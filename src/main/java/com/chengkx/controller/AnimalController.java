package com.chengkx.controller;


import org.springframework.web.bind.annotation.*;

@RestController
public class AnimalController {

    @GetMapping("/animal")
    public String getAnimal() {
        return "GET-获取动物";
    }

    @PostMapping("/animal")
    public String postAnimal() {
        return "POST-保存动物";
    }

    @PutMapping("/animal")
    public String putAnimal() {
        return "PUT-修改动物";
    }

    @DeleteMapping("/animal")
    public String delAnimal() {
        return "DELETE-删除动物";
    }
}