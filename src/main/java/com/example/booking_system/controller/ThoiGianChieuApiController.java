package com.example.booking_system.controller;

import com.example.booking_system.entity.Message;
import com.example.booking_system.entity.ThoiGianChieu;
import com.example.booking_system.service.ThoiGianChieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class ThoiGianChieuApiController {
    @Autowired
    ThoiGianChieuService thoiGianChieuService;

    @GetMapping("/thoigianchieu")
    public ResponseEntity<List<ThoiGianChieu>> layDanhSach() {
        List<ThoiGianChieu> lst = thoiGianChieuService.layDanhSach();
        return new ResponseEntity<>(lst, HttpStatus.OK);
    }

    @GetMapping("/thoigianchieu/{id}")
    public ResponseEntity<?> layChiTietTheoId(@PathVariable("id") int id) {
        ThoiGianChieu obj = thoiGianChieuService.layChiTiet(id);
        if (obj != null) {
            return new ResponseEntity<>(obj, HttpStatus.OK);
        } else {
            Message err = new Message("Không tìm thấy thời gian chiếu có mã: " + id);
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/thoigianchieu")
    public ResponseEntity<?> themMoi(@RequestBody ThoiGianChieu obj) {
        boolean kq = thoiGianChieuService.themMoi(obj);
        if (kq) {
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        Message msg = new Message("Không thể thêm mới thời gian chiếu");
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/thoigianchieu/{id}")
    public ResponseEntity<?> capNhat(@PathVariable("id") int id, @RequestBody ThoiGianChieu obj) {
        ThoiGianChieu old = thoiGianChieuService.layChiTiet(id);
        if (old == null) {
            Message msg = new Message("Không thể tìm thấy thời gian chiếu có id: " + id);
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } else {
            obj.setId(id);
            boolean kq = thoiGianChieuService.capNhat(obj);
            if (kq) {
                return new ResponseEntity<>(obj, HttpStatus.OK);
            } else {
                Message msg = new Message("Không cập nhật được thời gian chiếu có id: " + id);
                return new ResponseEntity<>(msg, HttpStatus.NOT_MODIFIED);
            }
        }
    }

    @DeleteMapping("/thoigianchieu/{id}")
    public ResponseEntity<?> xoa(@PathVariable("id") int id) {
        ThoiGianChieu obj = thoiGianChieuService.layChiTiet(id);
        if (obj == null) {
            Message msg = new Message("Không thể tìm thấy thời gian chiếu có id: " + id);
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        boolean kq = thoiGianChieuService.xoa(id);
        if (kq) {
            Message msg = new Message("Xóa thời gian chiếu có id: " + id + " thành công");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            Message msg = new Message("Không xóa được thời gian chiếu có id: " + id);
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }
}
