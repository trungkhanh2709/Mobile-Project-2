<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

include 'database.php';
include 'danhgia.php';

$response = array("error" => FALSE);
$database = new Database();
$db = $database->getConnection();
$danhgia = new DanhGia($db);

// Lấy dữ liệu gửi từ ứng dụng Android
$data = json_decode(file_get_contents("php://input"));

// Set các giá trị thuộc tính danhgia
$danhgia->MaPhim = $data->MaPhim;
$danhgia->MaNguoiDung = $data->MaNguoiDung;
$danhgia->DiemSo = $data->DiemSo;
$danhgia->BinhLuan = $data->BinhLuan;

// Thực hiện việc chèn dữ liệu vào cơ sở dữ liệu
if ($danhgia->insertData()) {
    $response["error"] = FALSE;
    $response["message"] = "Successful";
    echo json_encode($response);
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Insert failed";

    // Bổ sung thông tin chi tiết về lỗi
    $response["details"] = $danhgia->getLastError(); // Giả sử hàm getLastError() trả về thông tin lỗi từ lớp danhgia

    echo json_encode($response);
}
?>
