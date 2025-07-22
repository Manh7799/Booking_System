package com.example.booking_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RapChieuController {

    @RequestMapping("/admin/rapchieu")
    public String hienThidanhSachRapChieu() {
        return "admin/RapChieuCallApi";
    }
}
