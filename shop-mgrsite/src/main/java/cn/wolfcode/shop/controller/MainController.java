package cn.wolfcode.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//要返回页面使用Controoller不用restController
@Controller
public class MainController {

    @GetMapping({"/","/main"})
    public String main(){
        return "main";
    }

    @GetMapping({"/index"})
    public String index(){
        return "index";
    }
}
