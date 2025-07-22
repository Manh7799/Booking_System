function layDanhSachRapChieu() {
    $.ajax({
        type: "GET",
        url: "/api/rapchieu",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (result) {
            var thongtin = "";
            $.each(result, function (index, r) {
                thongtin += "<tr>";
                thongtin += "<td>" + r.idRapChieu + "</td>";
                thongtin += "<td>" + r.tenRap + "</td>";
                thongtin += "<td>" + r.diaChi + "</td>";
                var sua = "suaThongTin('" + r.idRapChieu + "')";
                var xoa = "xoaThongTin('" + r.idRapChieu + "')";
                thongtin += "<td><a data-bs-toggle=\"modal\" data-bs-target=\"#modalRapChieu\" href='#' onclick=\"" + sua + "\" title='Sửa rạp'>Sửa</a>&nbsp;";
                thongtin += "<a href='#' data-bs-toggle=\"modal\" data-bs-target=\"#modalXoaRap\" title='Xóa rạp' onclick=\"" + xoa + "\">Xóa</a></td>";
                thongtin += "</tr>";
            });
            $("#dsRapChieu").append(thongtin);
        }
    });
}

function xuLyThemMoi() {
    var id = $("#hRapChieuId").val();

    var urlPost = '/api/rapchieu';
    var methodType = "POST";

    //TH sửa
    if (id.length > 0) {
        urlPost = '/api/rapchieu/' + id;
        methodType = "PUT";
    }

    var idRapChieu = $("#idRapChieu").val();
    var tenRap = $("#tenRap").val();
    var diaChi = $("#diaChi").val();

    //Khai báo mảng
    var formData = {}
    formData["idRapChieu"] = idRapChieu;
    formData["tenRap"] = tenRap;
    formData["diaChi"] = diaChi;

    $.ajax({
        url: urlPost,
        contentType: "application/json; charset=utf-8;",
        dataType: "json",
        data: JSON.stringify(formData),
        type: methodType,
        success: function (data) {
            if (data.idRapChieu != null) {
                $("#modalRapChieu").modal("hide")
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

function thongTinChiTiet(idRapChieu) {
    $.ajax({
        type: "GET",
        url: "/api/rapchieu/" + idRapChieu,
        data: {
            id: idRapChieu
        },
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (data) {
            $("#modalTitleRap").text("Sửa thông tin rạp");
            $("#hRapChieuId").val(data.idRapChieu);
            $("#idRapChieu").val(data.idRapChieu);
            $("#tenRap").val(data.tenRap);
            $("#diaChi").val(data.diaChi);
        }
    });
}

function thucHienXoaRap() {
    var id = $("#hRapChieuId").val();
    $.ajax({
        url: '/api/rapchieu/' + id,
        contentType: "application/json; charset=utf-8;",
        dataType: "json",
        type: "DELETE",
        success: function (data) {
            if (data.name != null) {
                $("#modalXoaRap").modal("hide");
                window.location.reload();
            } else {
                $('#title-delete-rap').nextAll(".spanError").remove();
                $('#title-delete-rap').after('<div class="alert alert-dismissible alert-danger spanError">' + data.name + '</div>');
            }
        },
        error: function (error) {
            alert("Có lỗi xảy ra " + error.name);
        }
    });
}
