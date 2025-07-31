package com.example.booking_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DangNhapController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

}
