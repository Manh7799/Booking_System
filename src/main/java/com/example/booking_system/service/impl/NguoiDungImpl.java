package com.example.booking_system.service.impl;

import com.example.booking_system.dao.NguoiDungDao;
import com.example.booking_system.entity.NguoiDung;
import com.example.booking_system.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("nguoiDungDao")
public class NguoiDungImpl implements NguoiDungDao {

    @Autowired
    NguoiDungRepository nguoiDungRepository;

    @Override
    public List<NguoiDung> getList() {
        return nguoiDungRepository.findAll();
    }

    @Override
    public NguoiDung getById(Integer id) {
        NguoiDung objNguoiDung = null;
        objNguoiDung = nguoiDungRepository.findById(id).get();
        return objNguoiDung;
    }

    @Override
    public boolean add(NguoiDung obj) {
        NguoiDung objNguoiDung = nguoiDungRepository.save(obj);

        if (objNguoiDung != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean update(NguoiDung obj) {
        NguoiDung objNguoiDung = getById(obj.getIdNguoiDung());

        if (objNguoiDung != null) {
            if (obj.getTen() != null) objNguoiDung.setTen(obj.getTen());
            if (obj.getMatKhau() != null) objNguoiDung.setMatKhau(obj.getMatKhau());
            if (obj.getEmail() != null) objNguoiDung.setEmail(obj.getEmail());
            if (obj.getThoiGianTao() != null) objNguoiDung.setThoiGianTao(obj.getThoiGianTao());
            if (obj.getIdVaiTro() != 0) objNguoiDung.setIdVaiTro(obj.getIdVaiTro());

            nguoiDungRepository.save(objNguoiDung);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        NguoiDung objNguoiDung = getById(id);

        if (objNguoiDung != null) {
            nguoiDungRepository.delete(objNguoiDung);
            return true;
        }

        return false;
    }
}
