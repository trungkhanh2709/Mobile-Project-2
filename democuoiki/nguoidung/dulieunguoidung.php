<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'nguoidung.php';
$database=new Database(); 
$db=$database->getConnection();
$p= new NguoiDung ($db);
$stmt=$p->getData();
$num=$stmt->rowCount();

if ($num>0)
{
    $mangsp=array();
    $mangsp["nguoidungs"]=array();
    while ($row=$stmt->fetch (PDO:: FETCH_ASSOC))
    {
        extract ($row);
        $sp=array(
            "TenNguoiDung"=>$TenNguoiDung,
            "TaiKhoan"=>$TaiKhoan,
            "MatKhau"=>$MatKhau,
            "MaNguoiDung"=>$MaNguoiDung
        );
        array_push ($mangsp["nguoidungs"], $sp);
    }
    echo json_encode ($mangsp);
}
else
{
    echo json_encode(array("message"=>"Khong co nguoi dung"));
}
?>