<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'daodien.php';
$database=new Database(); 
$db=$database->getConnection();
$p= new DaoDien ($db);
$stmt=$p->getData();
$num=$stmt->rowCount();

if ($num>0)
{
    $mangsp=array();
    $mangsp["daodiens"]=array();
    while ($row=$stmt->fetch (PDO:: FETCH_ASSOC))
    {
        extract ($row);
        $sp=array(
            "MaDaoDien"=>$MaDaoDien,
            "TenDaoDien"=>$TenDaoDien,
            "QuocTich"=>$QuocTich,
            "NgayThangNamSinh"=>$NgayThangNamSinh,
            "UrlHinhAnh"=>$UrlHinhAnh,
        );
        array_push ($mangsp["daodiens"], $sp);
    }
    echo json_encode ($mangsp);
}
else
{
    echo json_encode(array("message"=>"Khong co dao dien"));
}
?>