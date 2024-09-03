<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'phim.php';

$database = new Database(); 
$db=$database->getConnection();
$p = new Phim($db);
$stmt=$p->getData();
$num=$stmt->rowCount();

if ($num > 0) {
    $mangsp = array();
    $mangsp["phims"] = array();
    
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC))
    {
        extract($row);
        
        $sp=array(
            "MaPhim"=>$MaPhim,
            "TenPhim"=>$TenPhim,
            "NamRaMat"=>$NamRaMat,
            "QuocGia"=>$QuocGia,
            "MoTaPhim"=>$MoTaPhim,
            "ThoiLuongPhim"=>$ThoiLuongPhim,
            "URLTrailer"=>$URLTrailer,
            "PosterPhim"=>$PosterPhim
        );
        
        array_push($mangsp["phims"], $sp);
    }
    
    echo json_encode($mangsp);
} else {
    echo json_encode(array("message" => "Khong co phim"));
}


?>