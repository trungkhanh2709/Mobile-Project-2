<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'phim_theloai.php';
$database=new Database(); 
$db=$database->getConnection();
$p= new phim_theloai ($db);
$stmt=$p->getData();
$num=$stmt->rowCount();

if ($num>0)
{
    $mangsp=array();
    $mangsp["phim_theloais"]=array();
    while ($row=$stmt->fetch (PDO:: FETCH_ASSOC))
    {
        extract ($row);
        $sp=array(
            "MaPhim"=>$MaPhim,
            "MaTheLoai"=>$MaTheLoai
        );
        array_push ($mangsp["phim_theloais"], $sp);
    }
    echo json_encode ($mangsp);
}
else
{
    echo json_encode(array("message"=>"Khong co "));
}
?>