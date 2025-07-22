package com.example.booking_system.service;

import com.example.booking_system.dao.TheLoaiDao;
import com.example.booking_system.entity.TheLoai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheLoaiService {

    @Autowired
    TheLoaiDao theLoaiDao;

    public List<TheLoai> layDanhSach() {
        return theLoaiDao.getList();
    }

    public TheLoai layChiTiet(int idTheLoai) {
        return theLoaiDao.getById(idTheLoai);
    }

    public boolean themMoi(TheLoai objTheLoai) {
        return theLoaiDao.add(objTheLoai);
    }

    public boolean capNhat(TheLoai objTheLoai) {
        return theLoaiDao.update(objTheLoai);
    }

    public boolean xoa(int idTheLoai) {
        return theLoaiDao.delete(idTheLoai);
    }
}

