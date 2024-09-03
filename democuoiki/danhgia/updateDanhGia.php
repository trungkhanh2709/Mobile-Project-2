<?php
// Kết nối đến cơ sở dữ liệu
$servername = "localhost";
$username = "root"; // Thay thế bằng tên người dùng MySQL của bạn
$password = ""; // Thay thế bằng mật khẩu của bạn
$dbname = "riviu_database"; // Thay thế bằng tên cơ sở dữ liệu của bạn

// Tạo kết nối đến cơ sở dữ liệu
$conn = new mysqli($servername, $username, $password, $dbname);

// Kiểm tra kết nối
if ($conn->connect_error) {
    die("Kết nối đến CSDL thất bại: " . $conn->connect_error);
}

// Lấy dữ liệu từ request của ứng dụng Android
$maPhim = $_POST['MaPhim'];
$maNguoiDung = $_POST['MaNguoiDung'];
$diemSo = $_POST['DiemSo'];
$binhLuan = $_POST['BinhLuan'];
$ngayBinhLuan = $_POST['NgayBinhLuan'];

// Query để chèn dữ liệu vào bảng danhgia
$sql = "UPDATE danhgia (MaPhim, MaNguoiDung, DiemSo, BinhLuan, NgayBinhLuan) VALUES ('$maPhim', '$maNguoiDung', '$diemSo', '$binhLuan', '$ngayBinhLuan')";

if ($conn->query($sql) === TRUE) {
    echo "Dữ liệu đã được chèn thành công";
} else {
    echo "Lỗi: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>
