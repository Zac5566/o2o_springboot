package com.imooc.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/local")
public class LocalAuthManagementController {

    //登入頁面
    @GetMapping(value = "/login")
    private String login(){
        return "local/login";
    }

    //修改密碼頁面
    @GetMapping(value = "/changepsw")
    private String changepsw(){
        return "local/changepsw";
    }
}
