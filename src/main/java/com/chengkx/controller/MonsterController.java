package com.chengkx.controller;

import com.chengkx.entity.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonsterController {

    @Autowired
    private Monster monster;

    @RequestMapping(value = "/monster")
    public Monster monster(){
        return monster;
    }

}
