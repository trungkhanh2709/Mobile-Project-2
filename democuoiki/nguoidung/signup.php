<?php
// Kết nối tới cơ sở dữ liệu MySQL mà không cần đăng nhập

$TenNguoiDung = $_POST["TenNguoiDung"];
$MatKhau = $_POST["MatKhau"];
$TenTaiKhoan = $_POST["TenTaiKhoan"];
$MaNguoiDung = $_POST["MaNguoiDung"];

require 'database.php';

if($con)
{
    $sql = "SELECT * FROM nguoidung"
    
}
$conn = new mysqli($servername, $username, $password, $dbname);

// Kiểm tra kết nối
if ($conn->connect_error) {
    die("Kết nối tới cơ sở dữ liệu thất bại: " . $conn->connect_error);
}

// Kiểm tra phương thức request từ ứng dụng Android
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Lấy dữ liệu từ ứng dụng Android
    $TenTaiKhoan = $_POST['TenTaiKhoan'];
    $TenNguoiDung = $_POST['TenNguoiDung'];
    $MatKhau = $_POST['MatKhau'];
    $MaNguoiDung = $_POST["MaNguoiDung"];

    // Tạo truy vấn SQL để chèn thông tin người dùng vào cơ sở dữ liệu
    $sql = "INSERT INTO nguoidung (TenTaiKhoan, TenNguoiDung, MatKhau, MaNguoiDung) VALUES ('$TenTaiKhoan', '$TenNguoiDung', '$MatKhau', '$MaNguoiDung')";

    if ($conn->query($sql) === TRUE) {
        // Gửi phản hồi về cho ứng dụng Android
        echo "Đăng ký thành công!";
    } else {
        echo "Đăng ký thất bại: " . $conn->error;
    }
}

// Đóng kết nối tới cơ sở dữ liệu
$conn->close();
?>
