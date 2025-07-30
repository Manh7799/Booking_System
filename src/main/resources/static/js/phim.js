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

// function layDanhSachPhim() {
//     $.ajax({
//         type: "GET",
//         url: "/api/phim",
//         dataType: "json",
//         contentType: "application/json; charset=utf-8",
//         async: true,
//         success: function (result) {
//             var thongtin = "";
//             //Duyệt từng dòng lấy được
//             $.each(result, function (index, p) {
//                 thongtin += "<tr>";
//                 thongtin += "<td>" + p.idPhim + "</td>";
//                 thongtin += "<td>" + p.anh + "</td>";
//                 thongtin += "<td>" + p.tenPhim + "</td>";
//                 thongtin += "<td>" + p.daoDien + "</td>";
//                 thongtin += "<td>" + p.dienVien + "</td>";
//                 thongtin += "<td>" + layTenTheLoai(p.idTheLoai) + "</td>";
//                 thongtin += "<td>" + p.khoiChieu + "</td>";
//                 thongtin += "<td>" + p.ngonNgu + "</td>";
//                 thongtin += "<td>" + p.thoiLuong + "</td>";
//                 thongtin += "<td>" + p.moTa + "</td>";
//                 var sua = "suaThongTin('" + p.idPhim + "')";
//                 var xoa = "xoaThongTin('" + p.idPhim + "')";
//                 thongtin += "<td><a data-bs-toggle=\"modal\" data-bs-target=\"#modalPhim\" href='#' onclick=\"" + sua + "\" title='Sửa phim'>Sửa</a>&nbsp;";
//                 thongtin += "<a href='#' data-bs-toggle=\"modal\" data-bs-target=\"#modalXoa\" title='Xóa phim' onclick=\"" + xoa + "\">Xóa</a></td>";
//                 thongtin += "</tr>";
//             });
//
//             $("#dsPhim").append(thongtin);
//         }
//     });
// }

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
            thongtin += "<td>" + p.idPhim + "</td>";
            thongtin += "<td>" + (p.anh || '') + "</td>";
            thongtin += "<td>" + p.tenPhim + "</td>";
            thongtin += "<td>" + (p.daoDien || '') + "</td>";
            thongtin += "<td>" + (p.dienVien || '') + "</td>";
            thongtin += "<td>" + layTenTheLoai(p.idTheLoai) + "</td>";
            thongtin += "<td>" + (p.khoiChieu || '') + "</td>";
            thongtin += "<td>" + (p.ngonNgu || '') + "</td>";
            thongtin += "<td>" + (p.thoiLuong || '') + "</td>";
            thongtin += "<td>" + (p.moTa || '') + "</td>";

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

function xuLyThemMoi() {
    //Lấy thông tin trên giao diện
    var id = $("#hPhimid").val();

    var urlPost = '/api/phim';
    var methodType = "POST";

    //TH sửa
    if (id.length > 0) {
        urlPost = '/api/phim/' + id;
        methodType = "PUT";
    }

    var idPhim = $("#idPhim").val();
    var tenPhim = $("#tenPhim").val();
    var daoDien = $("#daoDien").val();
    var dienVien = $("#dienVien").val();
    var idTheLoai = $("#idTheLoai").val();
    var khoiChieu = $("#khoiChieu").val();
    var ngonNgu = $("#ngonNgu").val();
    var thoiLuong = $("#thoiLuong").val();
    var moTa = $("#moTa").val();
    var anh = $("#anh").val();

    //Khai báo mảng
    var formData = {}
    formData["idPhim"] = idPhim;
    formData["tenPhim"] = tenPhim;
    formData["daoDien"] = daoDien;
    formData["dienVien"] = dienVien;
    formData["idTheLoai"] = idTheLoai;
    formData["khoiChieu"] = khoiChieu;
    formData["ngonNgu"] = ngonNgu;
    formData["thoiLuong"] = thoiLuong;
    formData["moTa"] = moTa;
    formData["anh"] = anh;

    $.ajax({
        url: urlPost,
        contentType: "application/json; charset=utf-8;",
        dataType: "json",
        data: JSON.stringify(formData),
        type: methodType,
        success: function (data) {
            if (data.idPhim != null) {
                $("#modalPhim").modal("hide")
                //Reload lại trang
                window.location.reload();
            } else {
                $('#tile-body').nextAll(".spanError").remove();
                $('#tile-body').after('<div class="alert alert-dismissible alert-danger spanError">' + data.name + '</div>')

            }
        },
        error: function (error) {
            alert("Có lỗi xảy ra " + error.name);
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
        data: {
            id: idPhim
        },
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (data) {
            //Hiển thị lên giao diện
            $("#modalTitle").text("Sửa thông tin phim");
            $("#hPhimid").val(data.idPhim);
            $("#idPhim").val(data.idPhim);
            $("#tenPhim").val(data.tenPhim);
            $("#daoDien").val(data.daoDien);
            $("#dienVien").val(data.dienVien);
            populateTheLoaiDropdown();
            $("#idTheLoai").val(data.idTheLoai);
            $("#khoiChieu").val(data.khoiChieu);
            $("#ngonNgu").val(data.ngonNgu);
            $("#thoiLuong").val(data.thoiLuong);
            $("#moTa").val(data.moTa);
            $("#anh").val(data.anh);
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

// Call the function to populate the dropdown when the page loads
$(document).ready(function() {
    layDanhSachTheLoai(function() {
        populateTheLoaiDropdown();
    });
});