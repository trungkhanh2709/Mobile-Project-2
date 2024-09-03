<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'theloai.php';
$database=new Database(); 
$db=$database->getConnection();
$p= new TheLoai ($db);
$stmt=$p->getData();
$num=$stmt->rowCount();

if ($num>0)
{
    $mangsp=array();
    $mangsp["theloais"]=array();
    while ($row=$stmt->fetch (PDO:: FETCH_ASSOC))
    {
        extract ($row);
        $sp=array(
            "MaTheLoai"=>$MaTheLoai,
            "TenTheLoai"=>$TenTheLoai,
        );
        array_push ($mangsp["theloais"], $sp);
    }
    echo json_encode ($mangsp);
}
else
{
    echo json_encode(array("message"=>"Khong co the loai"));
}
?>