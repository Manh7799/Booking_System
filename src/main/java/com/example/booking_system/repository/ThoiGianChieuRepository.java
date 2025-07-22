package com.example.booking_system.repository;

import com.example.booking_system.entity.ThoiGianChieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThoiGianChieuRepository extends JpaRepository<ThoiGianChieu, Integer> {
}
