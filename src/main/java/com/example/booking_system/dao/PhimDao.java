package com.example.booking_system.dao;

import com.example.booking_system.entity.Phim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhimDao extends IHanhDong<Phim, Integer> {
    Page<Phim> getListWithPagination(Pageable pageable);
}
