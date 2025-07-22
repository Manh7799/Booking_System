package com.example.booking_system.controller;

import com.example.booking_system.entity.Message;
import com.example.booking_system.entity.Phim;
import com.example.booking_system.entity.RapChieu;
import com.example.booking_system.service.RapChieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RapChieuApiController {

    @Autowired
    RapChieuService rapChieuService;

    @GetMapping("/rapchieu")
    public ResponseEntity<List<RapChieu>> layDanhSach() {
        List<RapChieu> lstRapChieu = rapChieuService.layDanhSach();

        return new ResponseEntity<List<RapChieu>>(lstRapChieu, HttpStatus.OK);
    }

    @GetMapping("/rapchieu/{id}")
    public ResponseEntity<?> layChiTietTheoId(@PathVariable("id") int id) {
        RapChieu obj = rapChieuService.layChiTiet(id);
        if (obj != null) {
            return new ResponseEntity<>(obj, HttpStatus.OK);
        } else {
            Message err = new Message("Không tìm thấy rạp chiếu có mã: " + id);
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/rapchieu")
    public ResponseEntity<?> themMoi(@RequestBody RapChieu obj) {
        boolean kq = rapChieuService.themMoi(obj);
        if (kq) {
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        Message msg = new Message("Không thể thêm mới rạp chiếu");
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/rapchieu/{id}")
    public ResponseEntity<?> capNhat(@PathVariable("id") int id, @RequestBody RapChieu obj) {
        RapChieu old = rapChieuService.layChiTiet(id);
        if (old == null) {
            Message msg = new Message("Không thể tìm thấy rạp chiếu có id: " + id);
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else {
            obj.setIdRapChieu(id);
            boolean kq = rapChieuService.capNhat(obj);
            if (kq) {
                return new ResponseEntity<>(obj, HttpStatus.OK);
            } else {
                Message msg = new Message("Không cập nhật được rạp chiếu có id: " + id);
                return new ResponseEntity<>(msg, HttpStatus.NOT_MODIFIED);
            }
        }
    }

    @DeleteMapping("/rapchieu/{id}")
    public ResponseEntity<?> xoa(@PathVariable("id") int id) {
        RapChieu old = rapChieuService.layChiTiet(id);
        if (old == null) {
            Message msg = new Message("Không thể tìm thấy rạp chiếu có id: " + id);
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else {
            boolean kq = rapChieuService.xoa(id);
            if (kq) {
                Message msg = new Message("Xóa rạp chiếu có id: " + id + " thành công");
                return new ResponseEntity<>(msg, HttpStatus.OK);
            } else {
                Message msg = new Message("Không xóa được rạp chiếu có id: " + id);
                return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
            }
        }
    }
}
