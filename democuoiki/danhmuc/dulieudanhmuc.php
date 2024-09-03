<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'danhmuc.php';
$database=new Database(); 
$db=$database->getConnection();
$p= new danhmuc ($db);
$stmt=$p->getData();
$num=$stmt->rowCount();

if ($num>0)
{
    $mangsp=array();
    $mangsp["danhmucs"]=array();
    while ($row=$stmt->fetch (PDO:: FETCH_ASSOC))
    {
        extract ($row);
        $sp=array(
            "MaDanhMuc"=>$MaDanhMuc,
            "TenDanhMuc"=>$TenDanhMuc
        );
        array_push ($mangsp["danhmucs"], $sp);
    }
    echo json_encode ($mangsp);
}
else
{
    echo json_encode(array("message"=>"Khong co "));
}
?>