let danhSachTheLoai = [];

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

function layDanhSachPhim() {
    $.ajax({
        type: "GET",
        url: "/api/phim",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (result) {
            var thongtin = "";
            //Duyệt từng dòng lấy được
            $.each(result, function (index, p) {
                thongtin += "<tr>";
                thongtin += "<td>" + p.idPhim + "</td>";
                thongtin += "<td>" + p.anh + "</td>";
                thongtin += "<td>" + p.tenPhim + "</td>";
                thongtin += "<td>" + p.daoDien + "</td>";
                thongtin += "<td>" + p.dienVien + "</td>";
                thongtin += "<td>" + layTenTheLoai(p.idTheLoai) + "</td>";
                thongtin += "<td>" + p.khoiChieu + "</td>";
                thongtin += "<td>" + p.ngonNgu + "</td>";
                thongtin += "<td>" + p.thoiLuong + "</td>";
                thongtin += "<td>" + p.moTa + "</td>";
                var sua = "suaThongTin('" + p.idPhim + "')";
                var xoa = "xoaThongTin('" + p.idPhim + "')";
                thongtin += "<td><a data-bs-toggle=\"modal\" data-bs-target=\"#modalPhim\" href='#' onclick=\"" + sua + "\" title='Sửa phim'>Sửa</a>&nbsp;";
                thongtin += "<a href='#' data-bs-toggle=\"modal\" data-bs-target=\"#modalXoa\" title='Xóa phim' onclick=\"" + xoa + "\">Xóa</a></td>";
                thongtin += "</tr>";
            });

            $("#dsPhim").append(thongtin);
        }
    });
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