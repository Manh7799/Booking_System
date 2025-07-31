function layDanhSachVaiTro() {
    $.ajax({
        type: "GET",
        url: "/api/vaitro",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (result) {
            var thongtin = "";
            $.each(result, function (index, r) {
                thongtin += "<tr>";
                thongtin += "<td>" + r.idVaiTro + "</td>";
                thongtin += "<td>" + r.tenVaiTro + "</td>";
                var sua = "suaThongTin('" + r.idVaiTro + "')";
                var xoa = "xoaThongTin('" + r.idVaiTro + "')";
                thongtin += "<td><a data-bs-toggle=\"modal\" data-bs-target=\"#modalVaiTro\" href='#' onclick=\"" + sua + "\" title='Sửa '>Sửa</a>&nbsp;";
                thongtin += "<a href='#' data-bs-toggle=\"modal\" data-bs-target=\"#modalXoa\" title='Xóa ' onclick=\"" + xoa + "\">Xóa</a></td>";
                thongtin += "</tr>";
            });
            $("#dsVaiTro").append(thongtin);
        }
    });
}

function xuLyThemMoi() {
    var id = $("#hVaiTroId").val();

    var urlPost = '/api/vaitro';
    var methodType = "POST";

    //TH sửa
    if (id.length > 0) {
        urlPost = '/api/vaitro/' + id;
        methodType = "PUT";
    }

    var idVaiTro = $("#idVaiTro").val();
    var tenVaiTro = $("#tenVaiTro").val();

    //Khai báo mảng
    var formData = {}
    formData["idVaiTro"] = idVaiTro;
    formData["tenVaiTro"] = tenVaiTro;

    $.ajax({
        url: urlPost,
        contentType: "application/json; charset=utf-8;",
        dataType: "json",
        data: JSON.stringify(formData),
        type: methodType,
        success: function (data) {
            if (data.idVaiTro != null) {
                $("#modalVaiTro").modal("hide")
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

function thongTinChiTiet(idVaiTro) {
    $.ajax({
        type: "GET",
        url: "/api/vaitro/" + idVaiTro,
        data: {
            id: idVaiTro
        },
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        async: true,
        success: function (data) {
            $("#modalTitle").text("Sửa thông tin");
            $("#hVaiTroId").val(data.idVaiTro);
            $("#idVaiTro").val(data.idVaiTro);
            $("#tenVaiTro").val(data.tenVaiTro);
        }
    });
}

function thucHienXoa() {
    var id = $("#hVaiTroId").val();
    $.ajax({
        url: '/api/vaitro/' + id,
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
