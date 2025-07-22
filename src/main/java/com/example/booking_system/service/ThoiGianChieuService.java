package com.example.booking_system.service;

import com.example.booking_system.entity.ThoiGianChieu;
import com.example.booking_system.dao.ThoiGianChieuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ThoiGianChieuService {
    @Autowired
    ThoiGianChieuDao thoiGianChieuDao;

    public List<ThoiGianChieu> layDanhSach() {
        return thoiGianChieuDao.getList();
    }

    public ThoiGianChieu layChiTiet(int id) {
        return thoiGianChieuDao.getById(id);
    }

    public boolean themMoi(ThoiGianChieu obj) {
        return thoiGianChieuDao.add(obj);
    }

    public boolean capNhat(ThoiGianChieu obj) {
        return thoiGianChieuDao.update(obj);
    }

    public boolean xoa(int id) {
        return thoiGianChieuDao.delete(id);
    }
}
