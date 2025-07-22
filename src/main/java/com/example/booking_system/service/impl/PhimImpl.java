package com.example.booking_system.service.impl;

import com.example.booking_system.dao.PhimDao;
import com.example.booking_system.entity.Phim;
import com.example.booking_system.repository.PhimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("phimDao")
public class PhimImpl implements PhimDao {

    @Autowired
    PhimRepository phimRepository;

    @Override
    public List<Phim> getList() {
        return phimRepository.findAll();
    }

    @Override
    public Phim getById(Integer id) {
        Phim objPhim = null;
        objPhim = phimRepository.findById(id).get();
        return objPhim;
    }

    @Override
    public boolean add(Phim obj) {

        Phim objPhim = phimRepository.save(obj);

        if (objPhim != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean update(Phim obj) {
        Phim objPhim = getById(obj.getIdPhim());

        if (objPhim != null) {
            if (obj.getTenPhim() != null) objPhim.setTenPhim(obj.getTenPhim());
            if (obj.getDaoDien() != null) objPhim.setDaoDien(obj.getDaoDien());
            if (obj.getDienVien() != null) objPhim.setDienVien(obj.getDienVien());
            if (obj.getIdTheLoai() != 0) objPhim.setIdTheLoai(obj.getIdTheLoai());
            if (obj.getKhoiChieu() != null) objPhim.setKhoiChieu(obj.getKhoiChieu());
            if (obj.getThoiLuong() != null) objPhim.setThoiLuong(obj.getThoiLuong());
            if (obj.getNgonNgu() != null) objPhim.setNgonNgu(obj.getNgonNgu());
            if (obj.getMoTa() != null) objPhim.setMoTa(obj.getMoTa());
            if (obj.getAnh() != null) objPhim.setAnh(obj.getAnh());

            phimRepository.save(objPhim);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        Phim objPhim = getById(id);

        if (objPhim != null) {
            phimRepository.delete(objPhim);
            return true;
        }

        return false;
    }
}
