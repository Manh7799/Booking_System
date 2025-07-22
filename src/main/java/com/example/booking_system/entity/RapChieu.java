package com.example.booking_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rap_chieu")
public class RapChieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rap_chieu")
    private int idRapChieu;

    @Column(name = "ten_rap", nullable = false, length = 255)
    private String tenRap;

    @Column(name = "dia_chi", nullable = false, length = 255)
    private String diaChi;

    // Getters and Setters
    public int getIdRapChieu() {
        return idRapChieu;
    }

    public void setIdRapChieu(int idRapChieu) {
        this.idRapChieu = idRapChieu;
    }

    public String getTenRap() {
        return tenRap;
    }

    public void setTenRap(String tenRap) {
        this.tenRap = tenRap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
