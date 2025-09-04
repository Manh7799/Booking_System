let danhSachTheLoai = [];
let currentPage = 1;
let itemsPerPage = 10;
let totalPages = 1;
let totalElements = 0;

function layDanhSachTheLoai(callback) {
    $.ajax({
        type: "GET",
        url: "/api/theloai",
        dataType: "json",
        success: function (result) {
            danhSachTheLoai = result;
            if (callback) callback();
        }
    });
}

function layTenTheLoai(id) {
    let theLoai = danhSachTheLoai.find(tl => tl.idTheLoai == id);
    return theLoai ? theLoai.tenTheLoai : id;
}

function populateTheLoaiDropdown() {
    var selectElement = $("#idTheLoai");
    selectElement.empty(); // Clear existing options
    selectElement.append('<option value="">-- Chọn thể loại --</option>');
    
    $.each(danhSachTheLoai, function(index, theLoai) {
        selectElement.append('<option value="' + theLoai.idTheLoai + '">' + theLoai.tenTheLoai + '</option>');
    });
}

function layDanhSachPhim(page = 1, size = 10) {
    // Hiển thị loading
    $("#dsPhim").html('<tr><td colspan="11" class="text-center">Đang tải dữ liệu...</td></tr>');

    $.ajax({
        type: "GET",
        url: `/api/phim?page=${page}&size=${size}`,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (result) {
            // Cập nhật thông tin phân trang
            currentPage = result.currentPage;
            totalPages = result.totalPages;
            totalElements = result.totalElements;
            itemsPerPage = result.size;

            // Hiển thị dữ liệu
            hienThiDanhSachPhim(result.content);

            // Tạo phân trang
            taoGiaoDienPhanTrang();

            // Cập nhật thông tin trang
            capNhatThongTinTrang();
        },
        error: function(xhr, status, error) {
            $("#dsPhim").html('<tr><td colspan="11" class="text-center text-danger">Lỗi khi tải dữ liệu!</td></tr>');
            console.error("Lỗi:", error);
        }
    });
}

function hienThiDanhSachPhim(danhSachPhim) {
    var thongtin = "";

    if (danhSachPhim.length === 0) {
        thongtin = '<tr><td colspan="11" class="text-center">Không có dữ liệu</td></tr>';
    } else {
        $.each(danhSachPhim, function (index, p) {
            thongtin += "<tr>";
            // Thêm timestamp vào URL ảnh để tránh cache
            const timestamp = new Date().getTime();
            const imgSrc = p.anh ? `/images/${p.anh}?t=${timestamp}` : '/images/default-movie.jpg';
            
            thongtin += "<td style='width: 50px;'>" + p.idPhim + "</td>";
            // Cố định kích thước ảnh 150x200px
            thongtin += '<td style="width: 150px;">' +
                      '<div style="width: 150px; height: 200px; overflow: hidden; display: flex; align-items: center; justify-content: center; background: #f5f5f5; margin: 0 auto;">' +
                      `<img src="${imgSrc}" alt="${p.tenPhim}" style="width: 100%; height: 100%; object-fit: cover;" onerror="this.onerror=null; this.src='/images/default-movie.jpg';">` +
                      '</div></td>';
            // Cố định kích thước cột ảnh
            // Định dạng lại ngày tháng năm
            let ngayKhoiChieu = '';
            if (p.khoiChieu) {
                try {
                    // Xử lý múi giờ Việt Nam (UTC+7)
                    const date = new Date(p.khoiChieu);
                    // Điều chỉnh về múi giờ Việt Nam (UTC+7)
                    const utc = date.getTime() + (date.getTimezoneOffset() * 60000);
                    const vnDate = new Date(utc + (3600000 * 7));

                    const day = String(vnDate.getDate()).padStart(2, '0');
                    const month = String(vnDate.getMonth() + 1).padStart(2, '0');
                    const year = vnDate.getFullYear();
                    ngayKhoiChieu = `${day}/${month}/${year}`;
                } catch (e) {
                    console.error('Lỗi định dạng ngày:', e);
                    // Nếu có lỗi, hiển thị nguyên gốc và cắt bỏ phần giờ phút giây
                    ngayKhoiChieu = p.khoiChieu.split('T')[0].split('-').reverse().join('/');
                }
            }

            thongtin += "<td style='width: 200px;'>" + p.tenPhim + "</td>";
            thongtin += "<td style='width: 100px;'>" + (p.daoDien || '') + "</td>";
            thongtin += "<td style='width: 200px;'>" + (p.dienVien || '') + "</td>";
            thongtin += "<td style='width: 100px;'>" + layTenTheLoai(p.idTheLoai) + "</td>";
            thongtin += "<td style='width: 100px;'>" + ngayKhoiChieu + "</td>";
            thongtin += "<td style='width: 100px;'>" + (p.ngonNgu || '') + "</td>";
            thongtin += "<td style='width: 80px;'>" + (p.thoiLuong || '') + "</td>";
            
            
            // Hiển thị mô tả theo chiều ngang
            let moTa = p.moTa || '';
            let moTaRutGon = moTa.length > 200 ? moTa.substring(0, 200) + '...' : moTa;
            thongtin += "<td style='max-width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;' title='" + moTa.replace(/"/g, '&quot;') + "'>" + moTaRutGon + "</td>";
            
            thongtin += "<td style='width: 100px;'>" + (p.trangThai || '') + "</td>";

            var sua = "suaThongTin('" + p.idPhim + "')";
            var xoa = "xoaThongTin('" + p.idPhim + "')";
            thongtin += "<td>";
            thongtin += "<a data-bs-toggle=\"modal\" data-bs-target=\"#modalPhim\" href='#' onclick=\"" + sua + "\" title='Sửa phim' class='btn btn-sm btn-warning me-1'>Sửa</a>";
            thongtin += "<a href='#' data-bs-toggle=\"modal\" data-bs-target=\"#modalXoa\" title='Xóa phim' onclick=\"" + xoa + "\" class='btn btn-sm btn-danger'>Xóa</a>";
            thongtin += "</td>";
            thongtin += "</tr>";
        });
    }

    $("#dsPhim").html(thongtin);
}

function xuLyThemMoi() {
    // Tạo đối tượng FormData để lưu trữ dữ liệu form
    var formData = new FormData();
    var id = $("#hPhimid").val();
    var urlPost = '/api/phim';
    var methodType = "POST";

    // TH sửa
    if (id.length > 0) {
        urlPost = '/api/phim/' + id;
        methodType = "PUT";
    }

    // Thêm dữ liệu vào FormData
    formData.append("tenPhim", $("#tenPhim").val());
    formData.append("daoDien", $("#daoDien").val());
    formData.append("dienVien", $("#dienVien").val());
    formData.append("idTheLoai", $("#idTheLoai").val());
    formData.append("khoiChieu", $("#khoiChieu").val());
    formData.append("ngonNgu", $("#ngonNgu").val());
    formData.append("thoiLuong", $("#thoiLuong").val());
    formData.append("moTa", $("#moTa").val());

    // Thêm file ảnh nếu có
    var fileInput = document.getElementById('anh');
    if (fileInput.files.length > 0) {
        formData.append("anh", fileInput.files[0]);
    }

    $.ajax({
        url: urlPost,
        type: methodType,
        data: formData,
        processData: false,  // Không xử lý dữ liệu
        contentType: false,  // Không đặt Content-Type
        success: function (data) {
            if (data.idPhim != null) {
                $("#modalPhim").modal("hide");
                window.location.reload();
            } else {
                $('#tile-body').nextAll(".spanError").remove();
                $('#tile-body').after('<div class="alert alert-dismissible alert-danger spanError">' + (data.name || 'Có lỗi xảy ra') + '</div>');
            }
        },
        error: function (xhr, status, error) {
            console.error("Lỗi:", error);
            alert("Có lỗi xảy ra: " + error);
        }
    });
}

/*
        * Hàm hiển thị thông tin chi tiết chủ đề bằng jquery ajax
*/
function thongTinChiTiet(idPhim) {
    $.ajax({
        type: "GET",
        url: "/api/phim/" + idPhim,
        data: { id: idPhim },
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (data) {
            // Hiển thị lên giao diện
            $("#modalTitle").text("Sửa thông tin phim");
            $("#hPhimid").val(data.idPhim);
            $("#idPhim").val(data.idPhim);
            $("#tenPhim").val(data.tenPhim);
            $("#daoDien").val(data.daoDien || '');
            $("#dienVien").val(data.dienVien || '');
            populateTheLoaiDropdown();
            $("#idTheLoai").val(data.idTheLoai || '');
            
            // Định dạng lại ngày khởi chiếu cho input date
            if (data.khoiChieu) {
                try {
                    const date = new Date(data.khoiChieu);
                    // Điều chỉnh về múi giờ Việt Nam (UTC+7)
                    const utc = date.getTime() + (date.getTimezoneOffset() * 60000);
                    const vnDate = new Date(utc + (3600000 * 7));
                    
                    const day = String(vnDate.getDate()).padStart(2, '0');
                    const month = String(vnDate.getMonth() + 1).padStart(2, '0');
                    const year = vnDate.getFullYear();
                    
                    // Định dạng YYYY-MM-DD cho input type="date"
                    const formattedDate = `${day}-${month}-${year}`;
                    $("#khoiChieu").val(formattedDate);
                } catch (e) {
                    console.error('Lỗi định dạng ngày:', e);
                    // Nếu có lỗi, giữ nguyên giá trị gốc
                    $("#khoiChieu").val(data.khoiChieu.split('T')[0]);
                }
            } else {
                $("#khoiChieu").val('');
            }
            
            $("#ngonNgu").val(data.ngonNgu || '');
            $("#thoiLuong").val(data.thoiLuong || '');
            $("#moTa").val(data.moTa || '');
            $("#trangThai").val(data.trangThai || '');
            
            // Xử lý hiển thị ảnh hiện tại
            if (data.anh) {
                $("#currentImageContainer").show();
                $("#currentImageName").text(data.anh);
                $("#currentImagePreview").attr("src", "/images/" + data.anh).show();
            } else {
                $("#currentImageContainer").hide();
            }
            
            // Reset input file
            $("#anh").val("");
            $("#imagePreview").html('');
        },
        error: function(xhr, status, error) {
            console.error("Lỗi khi lấy thông tin phim:", error);
            alert("Có lỗi xảy ra khi tải thông tin phim");
        }
    });
}

function thucHienXoa() {
    var id = $("#hPhimid").val();

    $.ajax({
        url: '/api/phim/' + id,
        contentType: "application/json; charset=utf-8;",
        dataType: "json",
        type: "DELETE",
        success: function (data) {
            if (data.name != null) {
                $("#modalXoa").modal("hide")
                //Reload lại trang
                window.location.reload();
            } else {
                $('#title-delete').nextAll(".spanError").remove();
                $('#title-delete').after('<div class="alert alert-dismissible alert-danger spanError">' + data.name + '</div>')

            }
        },
        error: function (error) {
            alert("Có lỗi xảy ra " + error.name);
        }
    });
}

function taoGiaoDienPhanTrang() {
    let paginationHtml = '';

    if (totalPages <= 1) {
        $("#pagination-container").html('');
        return;
    }

    paginationHtml += '<nav aria-label="Phân trang phim">';
    paginationHtml += '<ul class="pagination justify-content-center">';

    // Nút Previous
    if (currentPage > 1) {
        paginationHtml += `<li class="page-item">
            <a class="page-link" href="#" onclick="chuyenTrang(${currentPage - 1})">‹ Trước</a>
        </li>`;
    }

    // Các số trang
    let startPage = Math.max(1, currentPage - 2);
    let endPage = Math.min(totalPages, currentPage + 2);

    if (startPage > 1) {
        paginationHtml += `<li class="page-item">
            <a class="page-link" href="#" onclick="chuyenTrang(1)">1</a>
        </li>`;
        if (startPage > 2) {
            paginationHtml += '<li class="page-item disabled"><span class="page-link">...</span></li>';
        }
    }

    for (let i = startPage; i <= endPage; i++) {
        const activeClass = i === currentPage ? 'active' : '';
        paginationHtml += `<li class="page-item ${activeClass}">
            <a class="page-link" href="#" onclick="chuyenTrang(${i})">${i}</a>
        </li>`;
    }

    if (endPage < totalPages) {
        if (endPage < totalPages - 1) {
            paginationHtml += '<li class="page-item disabled"><span class="page-link">...</span></li>';
        }
        paginationHtml += `<li class="page-item">
            <a class="page-link" href="#" onclick="chuyenTrang(${totalPages})">${totalPages}</a>
        </li>`;
    }

    // Nút Next
    if (currentPage < totalPages) {
        paginationHtml += `<li class="page-item">
            <a class="page-link" href="#" onclick="chuyenTrang(${currentPage + 1})">Sau ›</a>
        </li>`;
    }

    paginationHtml += '</ul></nav>';

    $("#pagination-container").html(paginationHtml);
}

function chuyenTrang(page) {
    if (page >= 1 && page <= totalPages && page !== currentPage) {
        layDanhSachPhim(page, itemsPerPage);
    }
}

function capNhatThongTinTrang() {
    const start = (currentPage - 1) * itemsPerPage + 1;
    const end = Math.min(currentPage * itemsPerPage, totalElements);

    $("#page-info").html(`Hiển thị ${start}-${end} của ${totalElements} kết quả`);
}

function thayDoiSoLuongTrang() {
    const newSize = $("#itemsPerPage").val();
    console.log(newSize);
    itemsPerPage = parseInt(newSize);
    layDanhSachPhim(1, itemsPerPage);
}

// Call the function to populate the dropdown when the page loads
$(document).ready(function() {
    layDanhSachTheLoai(function() {
        populateTheLoaiDropdown();
    });
});