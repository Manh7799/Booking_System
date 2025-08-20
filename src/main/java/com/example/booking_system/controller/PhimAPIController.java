package com.example.booking_system.controller;

import com.example.booking_system.entity.Message;
import com.example.booking_system.entity.Phim;
import com.example.booking_system.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class PhimAPIController {

    @Autowired
    PhimService phimService;

//    @GetMapping("/phim")
//    public ResponseEntity<List<Phim>> layDanhSach() {
//        List<Phim> lstPhim = phimService.layDanhSach();
//
//        return new ResponseEntity<List<Phim>>(lstPhim, HttpStatus.OK);
//    }

    @GetMapping("/phim")
    public ResponseEntity<Map<String, Object>> layDanhSach(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            // Tạo đối tượng Pageable để phân trang
            Pageable pageable = PageRequest.of(page - 1, size);  // -1 vì page trong Spring bắt đầu từ 0

            // Gọi service để lấy dữ liệu phân trang
            Page<Phim> pageResult = phimService.layDanhSachPhanTrang(pageable);

            // Đóng gói dữ liệu trả về
            Map<String, Object> response = new HashMap<>();
            response.put("content", pageResult.getContent());       // Danh sách phim
            response.put("currentPage", page);                     // Trang hiện tại
            response.put("totalPages", pageResult.getTotalPages()); // Tổng số trang
            response.put("totalElements", pageResult.getTotalElements()); // Tổng số phần tử
            response.put("size", size);
            response.put("hasNext", pageResult.hasNext());
            response.put("hasPrevious", pageResult.hasPrevious());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Không thể lấy danh sách phim");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @PostMapping(value = "/phim", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> themMoiPhim(
            @RequestParam(value = "anh", required = false) MultipartFile file,
            @RequestParam String tenPhim,
            @RequestParam String daoDien,
            @RequestParam String dienVien,
            @RequestParam int idTheLoai,
            @RequestParam String khoiChieu,
            @RequestParam String ngonNgu,
            @RequestParam String thoiLuong,
            @RequestParam String moTa) {
        
        try {
            Phim phimMoi = new Phim();
            
            // Xử lý upload file nếu có
            if (file != null && !file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String filePath = "D:/Booking_Systen/Booking_System/src/main/resources/static/images/" + fileName;
                File dest = new File(filePath);
                file.transferTo(dest);
                phimMoi.setAnh(fileName);
            }
            
            // Chuyển đổi chuỗi ngày tháng sang đối tượng Date
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
            Date ngayKhoiChieu = dateFormat.parse(khoiChieu);
            
            // Thiết lập các thông tin cho phim mới
            phimMoi.setTenPhim(tenPhim);
            phimMoi.setDaoDien(daoDien);
            phimMoi.setDienVien(dienVien);
            phimMoi.setIdTheLoai(idTheLoai);
            phimMoi.setKhoiChieu(ngayKhoiChieu);
            phimMoi.setNgonNgu(ngonNgu);
            phimMoi.setThoiLuong(thoiLuong);
            phimMoi.setMoTa(moTa);
            
            boolean kq = phimService.themMoi(phimMoi);
            if (kq) {
                return new ResponseEntity<Phim>(phimMoi, HttpStatus.OK);
            } else {
                Message msg = new Message("Không thể thêm mới phim");
                return new ResponseEntity<Message>(msg, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message("Lỗi khi thêm mới phim: " + e.getMessage());
            return new ResponseEntity<Message>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/phim/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> capNhatPhim(
            @PathVariable("id") int id,
            @RequestParam(value = "anh", required = false) MultipartFile file,
            @RequestParam String tenPhim,
            @RequestParam String daoDien,
            @RequestParam String dienVien,
            @RequestParam int idTheLoai,
            @RequestParam String khoiChieu,
            @RequestParam String ngonNgu,
            @RequestParam String thoiLuong,
            @RequestParam String moTa) {

        Phim objPhim_2 = phimService.layChiTiet(id);

        if (objPhim_2 == null) {
            Message msg = new Message("Không thể tìm thấy phim có id: " + id);
            return new ResponseEntity<Message>(msg, HttpStatus.NOT_FOUND);
        } else {
            try {
                // Xử lý upload file nếu có
                if (file != null && !file.isEmpty()) {
                    String fileName = file.getOriginalFilename();
                    String filePath = "D:/Booking_Systen/Booking_System/src/main/resources/static/images/" + fileName;
                    File dest = new File(filePath);
                    file.transferTo(dest);
                    // Lưu tên file vào đối tượng phim
                    objPhim_2.setAnh(fileName);
                }

                // Chuyển đổi chuỗi ngày tháng sang đối tượng Date
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                Date ngayKhoiChieu = dateFormat.parse(khoiChieu);

                // Cập nhật các trường thông tin khác
                objPhim_2.setTenPhim(tenPhim);
                objPhim_2.setDaoDien(daoDien);
                objPhim_2.setDienVien(dienVien);
                objPhim_2.setIdTheLoai(idTheLoai);
                objPhim_2.setKhoiChieu(ngayKhoiChieu);
                objPhim_2.setNgonNgu(ngonNgu);
                objPhim_2.setThoiLuong(thoiLuong);
                objPhim_2.setMoTa(moTa);

                boolean kq = phimService.capNhat(objPhim_2);
                if (kq) {
                    return new ResponseEntity<Phim>(objPhim_2, HttpStatus.OK);
                } else {
                    Message msg = new Message("Không cập nhật được phim có id: " + id);
                    return new ResponseEntity<Message>(msg, HttpStatus.NOT_MODIFIED);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Message msg = new Message("Lỗi khi xử lý file: " + e.getMessage());
                return new ResponseEntity<Message>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
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