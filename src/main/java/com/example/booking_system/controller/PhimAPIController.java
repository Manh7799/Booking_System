package com.example.booking_system.controller;

import com.example.booking_system.entity.Message;
import com.example.booking_system.entity.Phim;
import com.example.booking_system.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class PhimAPIController {

    @Autowired
    PhimService phimService;

    @GetMapping("/phim")
    public ResponseEntity<List<Phim>> layDanhSach() {
        List<Phim> lstPhim = phimService.layDanhSach();

        return new ResponseEntity<List<Phim>>(lstPhim, HttpStatus.OK);
    }

    @GetMapping("/phim/{id}")
    public ResponseEntity<?> layChiTietTheoId(@PathVariable("id") int id) {
        Phim objPhim = phimService.layChiTiet(id);

        if (objPhim != null) {
            return new ResponseEntity<Phim>(objPhim, HttpStatus.OK);
        } else {
            Message err = new Message("Không tìm thấy phim có mã: " + id);
            return new ResponseEntity<Message>(err, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/phim")
    public ResponseEntity<?> themMoiPhim(@RequestBody Phim objPhim) {
        boolean kq = phimService.themMoi(objPhim);
        if (kq) {
            return new ResponseEntity<Phim>(objPhim, HttpStatus.OK);
        }

        Message msg = new Message("Không thể thêm mới phim");
        return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/phim/{id}")
    public ResponseEntity<?> capNhatPhim(@PathVariable("id") int id, @RequestBody Phim objPhim) {
        Phim objPhim_2 = phimService.layChiTiet(id);

        if (objPhim_2 == null) {
            Message msg = new Message("Không thể tìm thấy phim có id: " + id);
            return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
        }
        else {
            objPhim.setIdPhim(id);
            boolean kq = phimService.capNhat(objPhim);
            if (kq) {
                return new ResponseEntity<Phim>(objPhim, HttpStatus.OK);
            } else {
                Message msg = new Message("Không cập nhật được phim có id: " + id);
                return new ResponseEntity<Message>(msg, HttpStatus.NOT_MODIFIED);
            }
        }
    }

    @DeleteMapping("/phim/{id}")
    public ResponseEntity<?> xoaPhim(@PathVariable("id") int id) {
        Phim objPhim = phimService.layChiTiet(id);

        if (objPhim == null) {
            Message msg = new Message("Không thể tìm thấy phim có id: " + id);
            return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
        }
        {
            objPhim.setIdPhim(id);
            boolean kq = phimService.xoa(id);
            if (kq) {
                Message msg = new Message("Xóa phim có id: " + id + " thành công");
                return new ResponseEntity<Message>(msg, HttpStatus.OK);
            } else {
                Message msg = new Message("Không xóa được phim có id: " + id);
                return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
            }
        }
    }
}
