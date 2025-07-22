package com.example.booking_system.service;

import com.example.booking_system.entity.NguoiDung;
import com.example.booking_system.dao.NguoiDungDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NguoiDungService {

    @Autowired
    NguoiDungDao nguoiDungDao;

    public List<NguoiDung> layDanhSach() {
        return nguoiDungDao.getList();
    }

    public NguoiDung layChiTiet(int idNguoiDung) {
        return nguoiDungDao.getById(idNguoiDung);
    }

    public boolean themMoi(NguoiDung objNguoiDung) {
        return nguoiDungDao.add(objNguoiDung);
    }

    public boolean capNhat(NguoiDung objNguoiDung) {
        return nguoiDungDao.update(objNguoiDung);
    }

    public boolean xoa(int idNguoiDung) {
        return nguoiDungDao.delete(idNguoiDung);
    }
}
