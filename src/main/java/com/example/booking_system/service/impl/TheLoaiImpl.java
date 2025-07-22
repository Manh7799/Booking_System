package com.example.booking_system.service.impl;

import com.example.booking_system.dao.TheLoaiDao;
import com.example.booking_system.entity.Phim;
import com.example.booking_system.entity.TheLoai;
import com.example.booking_system.repository.TheLoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("theLoaiDao")
public class TheLoaiImpl implements TheLoaiDao {

    @Autowired
    TheLoaiRepository theLoaiRepository;

    @Override
    public List<TheLoai> getList() {
        return theLoaiRepository.findAll();
    }

    @Override
    public TheLoai getById(Integer id) {
        TheLoai objTheLoai = null;
        objTheLoai = theLoaiRepository.findById(id).get();
        return objTheLoai;
    }

    @Override
    public boolean add(TheLoai obj) {
        TheLoai objTheLoai = theLoaiRepository.save(obj);

        if (objTheLoai != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean update(TheLoai obj) {
        TheLoai old = getById(obj.getIdTheLoai());
        if (old != null) {
            if (obj.getTenTheLoai() != null) old.setTenTheLoai(obj.getTenTheLoai());
            if (obj.getMoTa() != null) old.setMoTa(obj.getMoTa());
            theLoaiRepository.save(old);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        TheLoai old = getById(id);
        if (old != null) {
            theLoaiRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

