<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include 'database.php';

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $data = json_decode(file_get_contents("php://input"));

    // Lấy thông tin đăng nhập từ yêu cầu POST
    $TaiKhoan = $data->TaiKhoan;
    $MatKhau = $data->MatKhau;

    // Kết nối đến cơ sở dữ liệu
    $db = new Database();
    $conn = $db->getConnection();

    // Truy vấn để kiểm tra thông tin đăng nhập
    $query = "SELECT * FROM nguoidung WHERE TaiKhoan = :TaiKhoan AND MatKhau = :MatKhau";
    $stmt = $conn->prepare($query);
    $stmt->bindParam(':TaiKhoan', $TaiKhoan);
    $stmt->bindParam(':MatKhau', $MatKhau);
    $stmt->execute();

    // Kiểm tra kết quả trả về từ cơ sở dữ liệu
    if ($stmt->rowCount() > 0) {
        // Đăng nhập thành công
        $response['success'] = true;
        $response['message'] = "Login successful";
    } else {
        // Đăng nhập thất bại
        $response['success'] = false;
        $response['message'] = "Login failed. Invalid credentials";
    }
} else {
    $response['success'] = false;
    $response['message'] = "Invalid request method";
}

// Trả về kết quả dưới dạng JSON
echo json_encode($response);
?>
