package com.example.booking_system.service;

import com.example.booking_system.dao.PhimDao;
import com.example.booking_system.entity.Phim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhimService {

    @Autowired
    PhimDao phimDao;

    public List<Phim> layDanhSach() {
        return phimDao.getList();
    }
    
    public List<Phim> layDanhSachDangChieu() {
        return phimDao.getList().stream()
                .filter(phim -> "Đang chiếu".equals(phim.getTrangThai()))
                .collect(Collectors.toList());
    }

    public Page<Phim> layDanhSachPhanTrang(Pageable pageable) {
        return phimDao.getListWithPagination(pageable);
    }

    public Phim layChiTiet(int idPhim) {
        return phimDao.getById(idPhim);
    }

    public boolean themMoi(Phim objPhim) {
        return phimDao.add(objPhim);
    }

    public boolean capNhat(Phim objPhim) {
        return phimDao.update(objPhim);
    }

    public boolean xoa(int idPhim) {
        return phimDao.delete(idPhim);
    }
}
