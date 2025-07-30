package com.example.booking_system.repository;

import com.example.booking_system.entity.Phim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhimRepository extends JpaRepository<Phim, Integer> {
    Page<Phim> findAllBy(Pageable pageable);
}
