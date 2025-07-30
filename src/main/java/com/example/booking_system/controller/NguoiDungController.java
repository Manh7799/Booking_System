package com.example.booking_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NguoiDungController {

    @GetMapping("/admin/nguoidung")
    public String hienThidanhSachNguoiDung() {
        return "admin/NguoiDungCallApi";
    }
}
