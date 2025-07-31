function layDanhSachNguoiDung() {
    $.ajax({
        type: "GET",
        url: "/api/nguoidung",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (result) {
            var thongtin = "";
            $.each(result, function (index, nd) {
                thongtin += "<tr>";
                thongtin += "<td>" + nd.idNguoiDung + "</td>";
                thongtin += "<td>" + nd.ten + "</td>";
                thongtin += "<td>" + nd.matKhau + "</td>";
                thongtin += "<td>" + nd.email + "</td>";
                thongtin += "<td>" + nd.thoiGianTao + "</td>";
                thongtin += "<td>" + nd.tenVaiTro + "</td>";
                var sua = "suaThongTin('" + nd.idNguoiDung + "')";
                var xoa = "xoaThongTin('" + nd.idNguoiDung + "')";
                thongtin += "<td><a data-bs-toggle=\"modal\" data-bs-target=\"#modalNguoiDung\" href='#' onclick=\"" + sua + "\" title='Sửa'>Sửa</a>&nbsp;";
                thongtin += "<a href='#' data-bs-toggle=\"modal\" data-bs-target=\"#modalXoa\" title='Xóa' onclick=\"" + xoa + "\">Xóa</a></td>";
                thongtin += "</tr>";
            });
            $("#dsNguoiDung").append(thongtin);
        }
    });
}

function xuLyThemMoi() {
    var id = $("#hNguoiDungId").val();

    var urlPost = '/api/nguoidung';
    var methodType = "POST";

    //TH sửa
    if (id.length > 0) {
        urlPost = '/api/nguoidung/' + id;
        methodType = "PUT";
    }

    var idNguoiDung = $("#idNguoiDung").val();
    var ten = $("#ten").val();
    var matKhau = $("#matKhau").val();
    var email = $("#email").val();
    var thoiGianTao = $("#thoiGianTao").val();
    var tenVaiTro = $("#tenVaiTro").val();

    //Khai báo mảng
    var formData = {}
    formData["idNguoiDung"] = idNguoiDung;
    formData["ten"] = ten;
    formData["matKhau"] = matKhau;
    formData["email"] = email;
    formData["thoiGianTao"] = thoiGianTao;
    formData["tenVaiTro"] = tenVaiTro;

    $.ajax({
        url: urlPost,
        contentType: "application/json; charset=utf-8;",
        dataType: "json",
        data: JSON.stringify(formData),
        type: methodType,
        success: function (data) {
            if (data.idNguoiDung != null) {
                $("#modalNguoiDung").modal("hide")
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

function thongTinChiTiet(idNguoiDung) {
    $.ajax({
        type: "GET",
        url: "/api/nguoidung/" + idNguoiDung,
        data: {
            id: idNguoiDung
        },
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (data) {
            $("#modalTitle").text("Sửa thông tin");
            $("#hNguoiDungId").val(data.idNguoiDung);
            $("#idNguoiDung").val(data.idNguoiDung);
            $("#ten").val(data.ten);
            $("#matKhau").val(data.matKhau);
            $("#email").val(data.email);
            $("#thoiGianTao").val(data.thoiGianTao);
            $("#tenVaiTro").val(data.tenVaiTro);
        }
    });
}

function thucHienXoa() {
    var id = $("#hNguoiDungId").val();
    $.ajax({
        url: '/api/nguoidung/' + id,
        contentType: "application/json; charset=utf-8;",
        dataType: "json",
        type: "DELETE",
        success: function (data) {
            if (data.name != null) {
                $("#modalXoa").modal("hide");
                window.location.reload();
            } else {
                $('#title-delete').nextAll(".spanError").remove();
                $('#title-delete').after('<div class="alert alert-dismissible alert-danger spanError">' + data.name + '</div>');
            }
        },
        error: function (error) {
            alert("Có lỗi xảy ra " + error.name);
        }
    });
}
