<?php
header ("Access-Control-Allow-Origin: *");
header ("Content-Type: application/json; charset=UTF-8");
header ("Access-Control-Allow-Methods: POST");
header ("Access-Control-Max-Age: 3600");
header ("Access-Control-Allow-Headers: Content-Type,
Access-Control-Allow-Headers, Authorization, X-Requested-With");
include 'database.php';
include 'dsxemsau.php';
$response = array ("error" => FALSE);
$database = new Database();
$db = $database->getConnection();
$dsxemsau= new DanhSachXemSau ($db);
// get posted data
$data = json_decode(file_get_contents("php://input"));
// set dsxemsau property values
   
    $dsxemsau->MaPhim = $data->MaPhim;
    $dsxemsau->MaNguoiDung = $data->MaNguoiDung;  
    if ($dsxemsau->insertData()) {
        $response["error"] = FALSE;
        $response["message"]="Succcessful";
        echo json_encode($response);
    }   
    else
    {
        $response["error"] = TRUE;
        $response["error_msg"] = "Them that bai";
        echo json_encode($response);
    }
?>









