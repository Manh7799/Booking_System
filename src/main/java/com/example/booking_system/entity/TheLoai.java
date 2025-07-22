package com.example.booking_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "the_loai")
public class TheLoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_the_loai")
    private int idTheLoai;

    @Column(name = "ten_the_loai",nullable = false, length = 50)
    private String tenTheLoai;

    @Column(name = "mo_ta", length = 255)
    private String moTa;

    // Getters and Setters
    public int getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(int idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
