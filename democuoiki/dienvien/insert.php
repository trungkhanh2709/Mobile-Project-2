<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset = UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include 'database.php';
include 'dienvien.php';
$response = array("error" => FALSE);
$database = new Database();
$db = $database -> getConnection();
$dienvien = new dienvien($db);
//get posted data
$data = json_decode(file_get_contents("php://input"));
//set product property values
$dienvien -> $MaDienVien = $data -> MaDienVien;
$dienvien -> $TenDienVien = $data -> TenDienVien;
$dienvien -> $QuocTich = $data -> QuocTich;
$dienvien -> $NgayThangNamSinh = $data -> NgayThangNamSinh;
$dienvien -> $UrlHinhAnh = $data -> UrlHinhAnh;
if($dienvien -> insertData())
{
    $response["error"] = FALSE;
    $response["message"] = "Successfull";
    echo json_encode($response);
}
else{
    $response["error"] = TRUE;
    $response["erroe_msg"] = "Them that bai";
    echo json_encode($response);
}
?>