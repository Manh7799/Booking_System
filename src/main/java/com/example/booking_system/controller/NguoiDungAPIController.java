package com.example.booking_system.controller;

import com.example.booking_system.entity.Message;
import com.example.booking_system.entity.NguoiDung;
import com.example.booking_system.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/nguoidung")
    public ResponseEntity<?> themMoiNguoiDung(@RequestBody NguoiDung objNguoiDung) {
        // Mã hóa mật khẩu trước khi lưu
        String encodedPassword = passwordEncoder.encode(objNguoiDung.getMatKhau());
        objNguoiDung.setMatKhau(encodedPassword);
        
        boolean kq = nguoiDungService.themMoi(objNguoiDung);
        if (kq) {
            // Không trả về mật khẩu đã mã hóa cho client
            objNguoiDung.setMatKhau("");
            return new ResponseEntity<NguoiDung>(objNguoiDung, HttpStatus.OK);
        }

        Message msg = new Message("Không thể thêm mới người dùng");
        return new ResponseEntity<Message>(msg, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/nguoidung/{id}")
    public ResponseEntity<?> capNhatNguoiDung(@PathVariable("id") int id, @RequestBody NguoiDung objNguoiDung) {
        NguoiDung existingUser = nguoiDungService.layChiTiet(id);

        if (existingUser == null) {
            Message msg = new Message("Không thể tìm thấy người dùng có id: " + id);
            return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
        } else {
            // Chỉ mã hóa mật khẩu nếu có thay đổi
            if (objNguoiDung.getMatKhau() != null && !objNguoiDung.getMatKhau().isEmpty()) {
                // Kiểm tra xem mật khẩu đã được mã hóa chưa
                if (!objNguoiDung.getMatKhau().startsWith("$2a$")) {
                    String encodedPassword = passwordEncoder.encode(objNguoiDung.getMatKhau());
                    objNguoiDung.setMatKhau(encodedPassword);
                }
            } else {
                // Giữ nguyên mật khẩu cũ nếu không có thay đổi
                objNguoiDung.setMatKhau(existingUser.getMatKhau());
            }
            
            objNguoiDung.setIdNguoiDung(id);
            boolean kq = nguoiDungService.capNhat(objNguoiDung);
            if (kq) {
                // Không trả về mật khẩu đã mã hóa cho client
                objNguoiDung.setMatKhau("");
                return new ResponseEntity<NguoiDung>(objNguoiDung, HttpStatus.OK);
            } else {
                Message msg = new Message("Không cập nhật được người dùng có id: " + id);
                return new ResponseEntity<Message>(msg, HttpStatus.BAD_REQUEST);
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
