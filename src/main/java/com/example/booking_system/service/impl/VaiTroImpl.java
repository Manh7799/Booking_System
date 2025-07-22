package com.example.booking_system.service.impl;

import com.example.booking_system.dao.VaiTroDao;
import com.example.booking_system.entity.VaiTro;
import com.example.booking_system.repository.VaiTroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("vaiTroDao")
public class VaiTroImpl implements VaiTroDao {

    @Autowired
    VaiTroRepository vaiTroRepository;

    @Override
    public List<VaiTro> getList() {
        return vaiTroRepository.findAll();
    }

    @Override
    public VaiTro getById(Integer id) {
        VaiTro objVaiTro = null;
        objVaiTro = vaiTroRepository.findById(id).get();
        return objVaiTro;
    }

    @Override
    public boolean add(VaiTro obj) {
        VaiTro objVaiTro = vaiTroRepository.save(obj);

        if (objVaiTro != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean update(VaiTro obj) {
        VaiTro objVaiTro = getById(obj.getIdVaiTro());

        if (objVaiTro != null) {
            if (obj.getTen() != null) objVaiTro.setTen(obj.getTen());

            vaiTroRepository.save(objVaiTro);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        VaiTro objVaiTro = getById(id);

        if (objVaiTro != null) {
            vaiTroRepository.delete(objVaiTro);
            return true;
        }

        return false;
    }
}
