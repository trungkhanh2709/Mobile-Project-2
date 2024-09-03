<?php
header("Access-Control-Allow-Origin:*");
header("Content-Type: application/json; charset = UTF-8");
include '../Connect/database.php';
include 'dienvien.php';
$database = new Database();
$db = $database -> getConnection();

$p = new DienVien($db);
$stmt = $p -> getData();
$num = $stmt -> rowCount();

if($num > 0)
{
    $mangsp = array();
    $mangsp["dienviens"] = array();
    while($row = $stmt -> fetch(PDO::FETCH_ASSOC))
    {
        extract($row);
        $sp = array(
        "MaDienVien"=>$MaDienVien,
        "TenDienVien"=>$TenDienVien,
        "QuocTich"=>$QuocTich,
        "NgayThangNamSinh"=>$NgayThangNamSinh,
        "UrlHinhAnh"=>$UrlHinhAnh
        );
        array_push($mangsp["dienviens"], $sp);
    }
    echo json_encode($mangsp);
}
else
{
    echo json_encode(array("message" => "Khong co dien vien"));
}
?>