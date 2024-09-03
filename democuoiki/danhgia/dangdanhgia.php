<?php
// Kết nối tới cơ sở dữ liệu MySQL mà không cần đăng nhập

$MaPhim = $_POST["MaPhim"];
$MaNguoiDung = $_POST["MaNguoiDung"];
$DiemSo = $_POST["DiemSo"];
$BinhLuan = $_POST["BinhLuan"];

require 'database.php';

$conn = new mysqli($servername, $username, $password, $dbname);

// Kiểm tra kết nối
if ($conn->connect_error) {
    die("Kết nối tới cơ sở dữ liệu thất bại: " . $conn->connect_error);
}

// Kiểm tra phương thức request từ ứng dụng Android
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Lấy dữ liệu từ ứng dụng Android
    $MaPhim = $_POST['MaPhim'];
    $MaNguoiDung = $_POST['MaNguoiDung'];
    $DiemSo = $_POST['DiemSo'];
    $BinhLuan = $_POST['BinhLuan'];

    // Tạo truy vấn SQL để chèn thông tin người dùng vào cơ sở dữ liệu
    $sql = "INSERT INTO danhgia (MaPhim, MaNguoiDung, DiemSo,BinhLuan) VALUES ('$MaPhim', '$MaNguoiDung', '$DiemSo','$BinhLuan')";

    if ($conn->query($sql) === TRUE) {
        // Gửi phản hồi về cho ứng dụng Android
        echo "Đăng thành công!";
    } else {
        echo "Đăng thất bại: " . $conn->error;
    }
}

// Đóng kết nối tới cơ sở dữ liệu
$conn->close();
?>
