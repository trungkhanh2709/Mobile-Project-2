<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'hinhanhphim.php';
$database=new Database(); 
$db=$database->getConnection();
$p= new hinhanhphim ($db);
$stmt=$p->getData();
$num=$stmt->rowCount();

if ($num>0)
{
    $mangsp=array();
    $mangsp["hinhanhphims"]=array();
    while ($row=$stmt->fetch (PDO:: FETCH_ASSOC))
    {
        extract ($row);
        $sp=array(
            "MaHinhAnh"=>$MaHinhAnh,
            "UrlHinhAnh"=>$UrlHinhAnh,
            "MaPhim"=>$MaPhim,
        );
        array_push ($mangsp["hinhanhphims"], $sp);
    }
    echo json_encode ($mangsp);
}
else
{
    echo json_encode(array("message"=>"Khong co hinh anh"));
}
?>