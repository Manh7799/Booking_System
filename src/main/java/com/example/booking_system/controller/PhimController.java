package com.example.booking_system.controller;

import com.example.booking_system.entity.Phim;
import com.example.booking_system.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class PhimController {

    @Autowired
    PhimService phimService;

    @RequestMapping("/admin/phim")
    public String hienThidanhSachPhim() {
        return "admin/PhimCallApi";
    }

    @RequestMapping( "/")
    public String danhSachPhim(Model model) {
        List<Phim> phimList = phimService.layDanhSach();

        model.addAttribute("listPhim", phimList);

        return "/index";
    }
}
