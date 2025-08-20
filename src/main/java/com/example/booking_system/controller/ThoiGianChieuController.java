package com.example.booking_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThoiGianChieuController {

    @RequestMapping("/admin/thoigianchieu")
    public String hienThidanhSachThoiGianChieu() {
        return "admin/ThoiGianChieuCallApi";
    }

}
