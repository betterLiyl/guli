package com.guli.teacher.controller;

import com.guli.common.result.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @PostMapping("/login")
    public Result login(){
        try {
            return  Result.ok().data("token","admin");
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.error();
        }
    }

    @GetMapping("/info")
    public Result info(){
        try {
            return  Result.ok().data("roles","[\"admin\"]")
                    .data("name","admin")
                    .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.error();
        }
    }
}
