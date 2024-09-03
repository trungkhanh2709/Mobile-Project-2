<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'daodien.php';

$database=new Database(); 
$db=$database->getConnection();

$p= new DaoDien ($db);

$ArrayTenDaoDien = $p->layDanhSachTenDaoDien();
$stmt=$p->getData();
$num=$stmt->rowCount();




foreach ($ArrayTenDaoDien as $TenDaoDien) {
    $stmt = $p->layThongTinPhim($TenDaoDien);
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

        $mangPhims[$TenDaoDien] = $phims;
    } else {
        $mangPhims[$TenDaoDien] = array(); // Nếu không có phim, tạo một mảng trống
    }
}

echo json_encode($mangPhims);


?>