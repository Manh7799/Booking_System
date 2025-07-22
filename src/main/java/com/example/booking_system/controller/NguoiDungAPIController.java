package com.example.booking_system.controller;

import com.example.booking_system.entity.Message;
import com.example.booking_system.entity.NguoiDung;
import com.example.booking_system.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class NguoiDungAPIController {

    @Autowired
    NguoiDungService nguoiDungService;

    @GetMapping("/nguoidung")
    public ResponseEntity<List<NguoiDung>> layDanhSach() {
        List<NguoiDung> lstNguoiDung = nguoiDungService.layDanhSach();

        return new ResponseEntity<List<NguoiDung>>(lstNguoiDung, HttpStatus.OK);
    }

    @GetMapping("/nguoidung/{id}")
    public ResponseEntity<?> layChiTietTheoId(@PathVariable("id") int id) {
        NguoiDung objNguoiDung = nguoiDungService.layChiTiet(id);

        if (objNguoiDung != null) {
            return new ResponseEntity<NguoiDung>(objNguoiDung, HttpStatus.OK);
        } else {
            Message err = new Message("Không tìm thấy người dùng có mã: " + id);
            return new ResponseEntity<Message>(err, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/nguoidung")
    public ResponseEntity<?> themMoiNguoiDung(@RequestBody NguoiDung objNguoiDung) {
        boolean kq = nguoiDungService.themMoi(objNguoiDung);
        if (kq) {
            return new ResponseEntity<NguoiDung>(objNguoiDung, HttpStatus.OK);
        }

        Message msg = new Message("Không thể thêm mới người dùng");
        return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/nguoidung/{id}")
    public ResponseEntity<?> capNhatNguoiDung(@PathVariable("id") int id, @RequestBody NguoiDung objNguoiDung) {
        NguoiDung objNguoiDung_2 = nguoiDungService.layChiTiet(id);

        if (objNguoiDung_2 == null) {
            Message msg = new Message("Không thể tìm thấy người dùng có id: " + id);
            return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
        }
        else {
            objNguoiDung.setIdNguoiDung(id);
            boolean kq = nguoiDungService.capNhat(objNguoiDung);
            if (kq) {
                return new ResponseEntity<NguoiDung>(objNguoiDung, HttpStatus.OK);
            } else {
                Message msg = new Message("Không cập nhật được người dùng có id: " + id);
                return new ResponseEntity<Message>(msg, HttpStatus.NOT_MODIFIED);
            }
        }
    }

    @DeleteMapping("/nguoidung/{id}")
    public ResponseEntity<?> xoaNguoiDung(@PathVariable("id") int id) {
        NguoiDung objNguoiDung = nguoiDungService.layChiTiet(id);

        if (objNguoiDung == null) {
            Message msg = new Message("Không thể tìm thấy người dùng có id: " + id);
            return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
        }
        {
            objNguoiDung.setIdNguoiDung(id);
            boolean kq = nguoiDungService.xoa(id);
            if (kq) {
                Message msg = new Message("Xóa người dùng có id: " + id + " thành công");
                return new ResponseEntity<Message>(msg, HttpStatus.OK);
            } else {
                Message msg = new Message("Không xóa được người dùng có id: " + id);
                return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
            }
        }
    }
}
