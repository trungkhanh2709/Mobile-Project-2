<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'danhgia.php';
$database=new Database(); 
$db=$database->getConnection();
$p= new DanhGia ($db);
$stmt=$p->getData();
$num=$stmt->rowCount();

if ($num>0)
{
    $mangsp=array();
    $mangsp["danhgias"]=array();
    while ($row=$stmt->fetch (PDO:: FETCH_ASSOC))
    {
        extract ($row);
        $sp=array(
            "MaDanhGia"=>$MaDanhGia,
            "MaPhim"=>$MaPhim,
            "MaNguoiDung"=>$MaNguoiDung,
            "DiemSo"=>$DiemSo,
            "BinhLuan"=>$BinhLuan,
            "NgayBinhLuan"=>$NgayBinhLuan
        );
        array_push ($mangsp["danhgias"], $sp);
    }
    echo json_encode ($mangsp);
}
else
{
    echo json_encode(array("message"=>"Khong co danh gia"));
}
?>