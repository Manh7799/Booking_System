package com.example.booking_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PhimController {

    @RequestMapping("/admin/phim")
    public String hienThidanhSachPhim() {
        return "admin/PhimCallApi";
    }
}
