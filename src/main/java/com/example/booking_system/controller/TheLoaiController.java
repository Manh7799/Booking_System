package com.example.booking_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TheLoaiController {

    @RequestMapping("/admin/theloai")
    public String hienThidanhSachTheLoai() {
        return "admin/TheLoaiCallApi";
    }
}
