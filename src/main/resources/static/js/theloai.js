function layDanhSachTheLoai() {
    $.ajax({
        type: "GET",
        url: "/api/theloai",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (result) {
            var thongtin = "";
            $.each(result, function (index, t) {
                thongtin += "<tr>";
                thongtin += "<td>" + t.idTheLoai + "</td>";
                thongtin += "<td>" + t.tenTheLoai + "</td>";
                thongtin += "<td>" + t.moTa + "</td>";
                var sua = "suaThongTin('" + t.idTheLoai + "')";
                var xoa = "xoaThongTin('" + t.idTheLoai + "')";
                thongtin += "<td><a data-bs-toggle=\"modal\" data-bs-target=\"#modalTheLoai\" href='#' onclick=\"" + sua + "\" title='Sửa'>Sửa</a>&nbsp;";
                thongtin += "<a href='#' data-bs-toggle=\"modal\" data-bs-target=\"#modalXoa\" title='Xóa' onclick=\"" + xoa + "\">Xóa</a></td>";
                thongtin += "</tr>";
            });
            $("#dsTheLoai").append(thongtin);
        }
    });
}

function xuLyThemMoi() {
    var id = $("#hTheLoaiId").val();

    var urlPost = '/api/theloai';
    var methodType = "POST";

    //TH sửa
    if (id.length > 0) {
        urlPost = '/api/theloai/' + id;
        methodType = "PUT";
    }

    var idTheLoai = $("#hTheLoaiId").val();
    var tenTheLoai = $("#tenTheLoai").val();
    var moTa = $("#moTa").val();

    //Khai báo mảng
    var formData = {}
    formData["idTheLoai"] = idTheLoai;
    formData["tenTheLoai"] = tenTheLoai;
    formData["moTa"] = moTa;

    $.ajax({
        url: urlPost,
        contentType: "application/json; charset=utf-8;",
        dataType: "json",
        data: JSON.stringify(formData),
        type: methodType,
        success: function (data) {
            if (data.idTheLoai != null) {
                $("#modalTheLoai").modal("hide")
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

function thongTinChiTiet(idTheLoai) {
    $.ajax({
        type: "GET",
        url: "/api/theloai/" + idTheLoai,
        data: {
            id: idTheLoai
        },
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (data) {
            $("#modalTitleRap").text("Sửa thông tin");
            $("#hTheLoaiId").val(data.idTheLoai);
            $("#idTheLoai").val(data.idTheLoai);
            $("#tenTheLoai").val(data.tenTheLoai);
            $("#moTa").val(data.moTa);
        }
    });
}

function thucHienXoa() {
    var id = $("#hTheLoaiId").val();
    $.ajax({
        url: '/api/theloai/' + id,
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
