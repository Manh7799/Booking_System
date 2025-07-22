package com.example.booking_system.controller;

import com.example.booking_system.entity.Message;
import com.example.booking_system.entity.VaiTro;
import com.example.booking_system.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class VaiTroAPIController {

    @Autowired
    VaiTroService vaiTroService;

    @GetMapping("/vaitro")
    public ResponseEntity<List<VaiTro>> layDanhSach() {
        List<VaiTro> lstVaiTro = vaiTroService.layDanhSach();

        return new ResponseEntity<List<VaiTro>>(lstVaiTro, HttpStatus.OK);
    }

    @GetMapping("/vaitro/{id}")
    public ResponseEntity<?> layChiTietTheoId(@PathVariable("id") int id) {
        VaiTro objVaiTro = vaiTroService.layChiTiet(id);

        if (objVaiTro != null) {
            return new ResponseEntity<VaiTro>(objVaiTro, HttpStatus.OK);
        } else {
            Message err = new Message("Không tìm thấy vai trò có mã: " + id);
            return new ResponseEntity<Message>(err, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/vaitro")
    public ResponseEntity<?> themMoiVaiTro(@RequestBody VaiTro objVaiTro) {
        boolean kq = vaiTroService.themMoi(objVaiTro);
        if (kq) {
            return new ResponseEntity<VaiTro>(objVaiTro, HttpStatus.OK);
        }

        Message msg = new Message("Không thể thêm mới vai trò");
        return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/vaitro/{id}")
    public ResponseEntity<?> capNhatVaiTro(@PathVariable("id") int id, @RequestBody VaiTro objVaiTro) {
        VaiTro objVaiTro_2 = vaiTroService.layChiTiet(id);

        if (objVaiTro_2 == null) {
            Message msg = new Message("Không thể tìm thấy vai trò có id: " + id);
            return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
        }
        else {
            objVaiTro.setIdVaiTro(id);
            boolean kq = vaiTroService.capNhat(objVaiTro);
            if (kq) {
                return new ResponseEntity<VaiTro>(objVaiTro, HttpStatus.OK);
            } else {
                Message msg = new Message("Không cập nhật được vai trò có id: " + id);
                return new ResponseEntity<Message>(msg, HttpStatus.NOT_MODIFIED);
            }
        }
    }

    @DeleteMapping("/vaitro/{id}")
    public ResponseEntity<?> xoaVaiTro(@PathVariable("id") int id) {
        VaiTro objVaiTro = vaiTroService.layChiTiet(id);

        if (objVaiTro == null) {
            Message msg = new Message("Không thể tìm thấy vai trò có id: " + id);
            return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
        }
        {
            objVaiTro.setIdVaiTro(id);
            boolean kq = vaiTroService.xoa(id);
            if (kq) {
                Message msg = new Message("Xóa vai trò có id: " + id + " thành công");
                return new ResponseEntity<Message>(msg, HttpStatus.OK);
            } else {
                Message msg = new Message("Không xóa được vai trò có id: " + id);
                return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
            }
        }
    }
}
