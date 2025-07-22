package com.example.booking_system.service.impl;

import com.example.booking_system.dao.RapChieuDao;
import com.example.booking_system.entity.Phim;
import com.example.booking_system.entity.RapChieu;
import com.example.booking_system.repository.RapChieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository("rapChieuDao")
public class RapChieuImpl implements RapChieuDao {

    @Autowired
    RapChieuRepository rapChieuRepository;

    @Override
    public List<RapChieu> getList() {
        return rapChieuRepository.findAll();
    }

    @Override
    public RapChieu getById(Integer id) {
        RapChieu objRapChieu = null;
        objRapChieu = rapChieuRepository.findById(id).get();
        return objRapChieu;
    }

    @Override
    public boolean add(RapChieu obj) {
        RapChieu objRapChieu = rapChieuRepository.save(obj);

        if (objRapChieu != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean update(RapChieu obj) {
        RapChieu old = getById(obj.getIdRapChieu());
        if (old != null) {
            if (obj.getTenRap() != null) old.setTenRap(obj.getTenRap());
            if (obj.getDiaChi() != null) old.setDiaChi(obj.getDiaChi());
            rapChieuRepository.save(old);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        RapChieu old = getById(id);
        if (old != null) {
            rapChieuRepository.delete(old);
            return true;
        }
        return false;
    }
}
