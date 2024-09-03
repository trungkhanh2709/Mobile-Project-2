<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include 'database.php';
include 'nguoidung.php';

if(isset($_POST['TenNguoiDung']) && isset($_POST['TaiKhoan']) && isset($_POST['MatKhau'])) {
    // Tiếp tục xử lý
    $tenNguoiDung = $_POST['TenNguoiDung'];
    $taiKhoan = $_POST['TaiKhoan'];
    $matKhau = $_POST['MatKhau'];

    // Tiếp tục thực hiện truy vấn SQL và các thao tác với cơ sở dữ liệu
} else {
    // Xử lý khi không có đủ dữ liệu được gửi đến
    echo "Dữ liệu không hợp lệ";
}


// Tạo truy vấn Insert
$sql = "INSERT INTO nguoidung (TenNguoiDung, TaiKhoan, MatKhau) VALUES ('$tenNguoiDung', '$taiKhoan', '$matKhau')";

if ($conn->query($sql) === TRUE) {
    echo "Thêm dữ liệu thành công";
} else {
    echo "Lỗi: " . $sql . "<br>" . $conn->error;
}

// Đóng kết nối
$conn->close();
?>