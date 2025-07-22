package com.example.booking_system.controller;

import com.example.booking_system.entity.Message;
import com.example.booking_system.entity.TheLoai;
import com.example.booking_system.service.TheLoaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class TheLoaiApiController {

    @Autowired
    TheLoaiService theLoaiService;

    @GetMapping("/theloai")
    public ResponseEntity<List<TheLoai>> layDanhSach() {
        List<TheLoai> lstTheLoai = theLoaiService.layDanhSach();
        return new ResponseEntity<List<TheLoai>>(lstTheLoai, HttpStatus.OK);
    }

    @GetMapping("/theloai/{id}")
    public ResponseEntity<?> layChiTietTheoId(@PathVariable("id") int id) {
        TheLoai objTheLoai = theLoaiService.layChiTiet(id);
        if (objTheLoai != null) {
            return new ResponseEntity<TheLoai>(objTheLoai, HttpStatus.OK);
        } else {
            Message err = new Message("Không tìm thấy thể loại có mã: " + id);
            return new ResponseEntity<Message>(err, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/theloai")
    public ResponseEntity<?> themMoiTheLoai(@RequestBody TheLoai objTheLoai) {
        boolean kq = theLoaiService.themMoi(objTheLoai);
        if (kq) {
            return new ResponseEntity<TheLoai>(objTheLoai, HttpStatus.OK);
        }
        Message msg = new Message("Không thể thêm mới thể loại");
        return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/theloai/{id}")
    public ResponseEntity<?> capNhatTheLoai(@PathVariable("id") int id, @RequestBody TheLoai objTheLoai) {
        TheLoai objTheLoai_2 = theLoaiService.layChiTiet(id);
        if (objTheLoai_2 == null) {
            Message msg = new Message("Không thể tìm thấy thể loại có id: " + id);
            return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
        } else {
            objTheLoai.setIdTheLoai(id);
            boolean kq = theLoaiService.capNhat(objTheLoai);
            if (kq) {
                return new ResponseEntity<TheLoai>(objTheLoai, HttpStatus.OK);
            } else {
                Message msg = new Message("Không cập nhật được thể loại có id: " + id);
                return new ResponseEntity<Message>(msg, HttpStatus.NOT_MODIFIED);
            }
        }
    }

    @DeleteMapping("/theloai/{id}")
    public ResponseEntity<?> xoaTheLoai(@PathVariable("id") int id) {
        TheLoai objTheLoai = theLoaiService.layChiTiet(id);
        if (objTheLoai == null) {
            Message msg = new Message("Không thể tìm thấy thể loại có id: " + id);
            return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
        }
        {
            boolean kq = theLoaiService.xoa(id);
            if (kq) {
                return new ResponseEntity<TheLoai>(objTheLoai, HttpStatus.OK);
            } else {
                Message msg = new Message("Không xóa được thể loại có id: " + id);
                return new ResponseEntity<Message>(msg, HttpStatus.NOT_MODIFIED);
            }
        }
    }
}
