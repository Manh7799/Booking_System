package com.example.booking_system.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "phim")
public class Phim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPhim;

    @Column(name = "ten_phim", nullable = false, length = 255)
    private String tenPhim;

    @Column(name = "dao_dien", nullable = true, length = 100)
    private String daoDien;

    @Column(name = "dien_vien", nullable = true, length = 255)
    private String dienVien;

    @Column(name = "id_the_loai", nullable = false)
    private int idTheLoai;

    @Column(name = "khoi_chieu", nullable = false)
    private Date khoiChieu;

    @Column(name = "thoi_luong", nullable = true, length = 50)
    private String thoiLuong;

    @Column(name = "ngon_ngu", nullable = true, length = 50)
    private String ngonNgu;

    @Column(name = "mo_ta", nullable = true, length = 500)
    private String moTa;

    @Column(name = "anh", nullable = true, length = 255)
    private String anh;

    @Column(name = "trang_thai", nullable = true, length = 50)
    private String trangThai;

    public int getIdPhim() {
        return idPhim;
    }

    public void setIdPhim(int idPhim) {
        this.idPhim = idPhim;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getDaoDien() {
        return daoDien;
    }

    public void setDaoDien(String daoDien) {
        this.daoDien = daoDien;
    }

    public String getDienVien() {
        return dienVien;
    }

    public void setDienVien(String dienVien) {
        this.dienVien = dienVien;
    }

    public int getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(int idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public Date getKhoiChieu() {
        return khoiChieu;
    }

    public void setKhoiChieu(Date khoiChieu) {
        this.khoiChieu = khoiChieu;
    }

    public String getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(String thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
