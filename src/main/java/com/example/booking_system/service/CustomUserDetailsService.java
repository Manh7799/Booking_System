package com.example.booking_system.service;

import com.example.booking_system.config.CustomUserDetails;
import com.example.booking_system.entity.NguoiDung;
import com.example.booking_system.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    NguoiDungRepository nguoiDungRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng"));
//        return new CustomUserDetails(nguoiDung);
//    }

    @Override
    public UserDetails loadUserByUsername(String ten) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepository.findByTen(ten)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng"));
        return new CustomUserDetails(nguoiDung);
    }


}
