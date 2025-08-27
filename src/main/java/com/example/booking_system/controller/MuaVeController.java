package com.example.booking_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MuaVeController {

    @RequestMapping("/user/muave")
    public String muaVe () {
        return "/MuaVe";
    }
}
