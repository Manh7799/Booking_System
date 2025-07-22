package com.example.booking_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "thoi_gian_chieu")
public class ThoiGianChieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "id_phim", nullable = false)
    private int idPhim;

    @Column(name = "id_rap_chieu", nullable = false)
    private int idRapChieu;

    @Column(name = "ngay_chieu", nullable = false)
    private String ngayChieu;

    @Column(name = "thoi_gian_chieu", nullable = false, length = 50)
    private String thoiGianChieu;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPhim() {
        return idPhim;
    }

    public void setIdPhim(int idPhim) {
        this.idPhim = idPhim;
    }

    public int getIdRapChieu() {
        return idRapChieu;
    }

    public void setIdRapChieu(int idRapChieu) {
        this.idRapChieu = idRapChieu;
    }

    public String getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(String ngayChieu) {
        this.ngayChieu = ngayChieu;
    }

    public String getThoiGianChieu() {
        return thoiGianChieu;
    }

    public void setThoiGianChieu(String thoiGianChieu) {
        this.thoiGianChieu = thoiGianChieu;
    }
}
