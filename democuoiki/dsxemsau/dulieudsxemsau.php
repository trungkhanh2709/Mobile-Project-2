<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'dsxemsau.php';
$database=new Database(); 
$db=$database->getConnection();
$p= new DanhSachXemSau ($db);
$stmt=$p->getData();
$num=$stmt->rowCount();

if ($num>0)
{
    $mangsp=array();
    $mangsp["dsxemsaus"]=array();
    while ($row=$stmt->fetch (PDO:: FETCH_ASSOC))
    {
        extract ($row);
        $sp=array(
            "MaPhim"=>$MaPhim,
            "MaNguoiDung"=>$MaNguoiDung
        );
        array_push ($mangsp["dsxemsaus"], $sp);
    }
    echo json_encode ($mangsp);
}
else
{
    echo json_encode(array("message"=>"Khong co danh gia"));
}
?>