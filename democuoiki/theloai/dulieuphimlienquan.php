<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'theloai.php';
$database=new Database(); 
$db=$database->getConnection();

$p= new TheLoai ($db);

$soLuongPhim = $p->countPhim();
$stmt=$p->getData();
$num=$stmt->rowCount();

$allMaPhim = array();
$maPhim = 5;
for ($i = 1; $i <= $soLuongPhim+5; $i++) {
    array_push($allMaPhim, $maPhim++);
}


foreach ($allMaPhim as $maPhim) {
    $stmt = $p->getPhimByTheLoai($maPhim);
    $num = $stmt->rowCount();

    if ($num > 0) {
        $phims = array();

        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            extract($row);
           $sp=array(
            "MaPhim"=>$MaPhim,
            "TenPhim"=>$TenPhim,
            "NamRaMat"=>$NamRaMat,
            "QuocGia"=>$QuocGia,
            "MoTaPhim"=>$MoTaPhim,
            "ThoiLuongPhim"=>$ThoiLuongPhim,
            "URLTrailer"=>$URLTrailer,
            "PosterPhim"=>$PosterPhim,
        );
            $phims[] = $sp;
        }

        $mangPhims[$maPhim] = $phims;
    } else {
        $mangPhims[$maPhim] = array(); // Nếu không có phim, tạo một mảng trống
    }
}

echo json_encode($mangPhims);


?>