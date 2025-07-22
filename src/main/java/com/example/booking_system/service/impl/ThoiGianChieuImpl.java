package com.example.booking_system.service.impl;

import com.example.booking_system.dao.ThoiGianChieuDao;
import com.example.booking_system.entity.Phim;
import com.example.booking_system.entity.ThoiGianChieu;
import com.example.booking_system.repository.ThoiGianChieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("thoiGianChieuDao")
public class ThoiGianChieuImpl implements ThoiGianChieuDao {
    @Autowired
    ThoiGianChieuRepository thoiGianChieuRepository;

    @Override
    public List<ThoiGianChieu> getList() {
        return thoiGianChieuRepository.findAll();
    }

    @Override
    public ThoiGianChieu getById(Integer id) {
        ThoiGianChieu obj = null;
        obj = thoiGianChieuRepository.findById(id).get();
        return obj;
    }

    @Override
    public boolean add(ThoiGianChieu obj) {
        ThoiGianChieu objTgc = thoiGianChieuRepository.save(obj);

        if (objTgc != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean update(ThoiGianChieu obj) {
        ThoiGianChieu old = getById(obj.getId());
        if (old != null) {
            if (obj.getNgayChieu() != null) old.setNgayChieu(obj.getNgayChieu());
            if (obj.getThoiGianChieu() != null) old.setThoiGianChieu(obj.getThoiGianChieu());
            thoiGianChieuRepository.save(obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        ThoiGianChieu old = getById(id);
        if (old != null) {
            thoiGianChieuRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
