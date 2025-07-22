package com.example.booking_system.service;

import com.example.booking_system.entity.VaiTro;
import com.example.booking_system.dao.VaiTroDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaiTroService {

    @Autowired
    VaiTroDao vaiTroDao;

    public List<VaiTro> layDanhSach() {
        return vaiTroDao.getList();
    }

    public VaiTro layChiTiet(int idVaiTro) {
        return vaiTroDao.getById(idVaiTro);
    }

    public boolean themMoi(VaiTro objVaiTro) {
        return vaiTroDao.add(objVaiTro);
    }

    public boolean capNhat(VaiTro objVaiTro) {
        return vaiTroDao.update(objVaiTro);
    }

    public boolean xoa(int idVaiTro) {
        return vaiTroDao.delete(idVaiTro);
    }
}
