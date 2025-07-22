package com.example.booking_system.service;

import com.example.booking_system.dao.RapChieuDao;
import com.example.booking_system.entity.RapChieu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RapChieuService {

    @Autowired
    RapChieuDao rapChieuDao;

    public List<RapChieu> layDanhSach() {
        return rapChieuDao.getList();
    }

    public RapChieu layChiTiet(int idRapChieu) {
        return rapChieuDao.getById(idRapChieu);
    }

    public boolean themMoi(RapChieu objRapChieu) {
        return rapChieuDao.add(objRapChieu);
    }

    public boolean capNhat(RapChieu objRapChieu) {
        return rapChieuDao.update(objRapChieu);
    }

    public boolean xoa(int idRapChieu) {
        return rapChieuDao.delete(idRapChieu);
    }
}
