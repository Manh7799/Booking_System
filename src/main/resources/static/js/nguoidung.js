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
                thongtin += "<td>********</td>"; // Ẩn mật khẩu
                thongtin += "<td>" + nd.email + "</td>";
                thongtin += "<td>" + nd.thoiGianTao + "</td>";
                thongtin += "<td>" + (nd.tenVaiTro || '') + "</td>";
                var sua = "suaThongTin('" + nd.idNguoiDung + "')";
                var xoa = "xoaThongTin('" + nd.idNguoiDung + "')";
                thongtin += "<td><a data-bs-toggle=\"modal\" data-bs-target=\"#modalNguoiDung\" href='#' onclick=\"" + sua + "\" title='Sửa'>Sửa</a>&nbsp;";
                thongtin += "<a href='#' data-bs-toggle=\"modal\" data-bs-target=\"#modalXoa\" title='Xóa' onclick=\"" + xoa + "\">Xóa</a></td>";
                thongtin += "</tr>";
            });
            $("#dsNguoiDung").html(thongtin); // Sửa từ append thành html để tránh lặp lại dữ liệu
        },
        error: function(xhr, status, error) {
            console.error("Lỗi khi tải danh sách người dùng:", error);
            alert("Có lỗi xảy ra khi tải danh sách người dùng");
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

    // Validate dữ liệu
    if (!ten || !email) {
        alert('Vui lòng điền đầy đủ thông tin bắt buộc (Tên và Email)');
        return;
    }

    // Nếu là thêm mới hoặc có nhập mật khẩu mới
    if (id.length === 0 || (matKhau && matKhau.length > 0)) {
        if (!matKhau || matKhau.length < 3) {
            alert('Mật khẩu phải có ít nhất 3 ký tự');
            return;
        }
    }

    // Kiểm tra định dạng email
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert('Vui lòng nhập đúng định dạng email');
        return;
    }

    // Khai báo mảng
    var formData = {}
    formData["idNguoiDung"] = idNguoiDung;
    formData["ten"] = ten;
    // Chỉ gửi mật khẩu nếu có thay đổi hoặc là thêm mới
    if (matKhau && matKhau.length > 0) {
        formData["matKhau"] = matKhau;
    }
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
                $("#modalNguoiDung").modal("hide");
                //Reload lại trang
                window.location.reload();
            } else {
                $('#tile-body').nextAll(".spanError").remove();
                let errorMessage = data.message || 'Có lỗi xảy ra khi xử lý yêu cầu';
                $('#tile-body').after('<div class="alert alert-dismissible alert-danger spanError">' + errorMessage + '</div>');
            }
        },
        error: function (xhr, status, error) {
            console.error('Lỗi chi tiết:', {
                status: xhr.status,
                statusText: xhr.statusText,
                responseText: xhr.responseText,
                error: error
            });
            
            let errorMessage = 'Có lỗi xảy ra: ';
            try {
                const response = JSON.parse(xhr.responseText);
                errorMessage += response.message || xhr.statusText;
            } catch (e) {
                errorMessage += xhr.statusText || error || 'Không xác định';
            }
            
            alert(errorMessage);
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
            // Không hiển thị mật khẩu đã mã hóa, để trống để người dùng nhập mật khẩu mới nếu muốn đổi
            $("#matKhau").val("");
            $("#email").val(data.email);
            $("#thoiGianTao").val(data.thoiGianTao);
            $("#tenVaiTro").val(data.tenVaiTro || '');
            
            // Thêm placeholder để hướng dẫn người dùng
            $("#matKhau").attr("placeholder", "Để trống nếu không đổi mật khẩu");
        },
        error: function(xhr, status, error) {
            console.error("Lỗi khi lấy thông tin người dùng:", error);
            alert("Có lỗi xảy ra khi tải thông tin người dùng");
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
