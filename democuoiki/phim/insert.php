<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include 'database.php';
include 'phim.php';

$response = array("error" => FALSE);
$database = new Database();
$db = $database->getConnection();
$phim = new Phim($db);

// Lấy dữ liệu được gửi qua phương thức POST
$data = json_decode(file_get_contents("php://input"));

// Thiết lập các thuộc tính của đối tượng Phim
$phim->MaPhim = $data->MaPhim;
$phim->TenPhim = $data->TenPhim;
$phim->NamSanXuat = $data->NamSanXuat;
$phim->QuocGia = $data->QuocGia;
$phim->MoTaPhim = $data->MoTaPhim;
$phim->URLTrailer = $data->URLTrailer;
$phim->PosterPhim = $data->PosterPhim;

// Thực hiện chèn dữ liệu vào bảng phim
if ($phim->insertData()) {
    $response["error"] = FALSE;
    $response["message"] = "Successful";
    echo json_encode($response);
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Thêm thất bại";
    echo json_encode($response);
}

?>









