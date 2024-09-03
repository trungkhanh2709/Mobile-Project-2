<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'dienvien.php';

$database=new Database(); 
$db=$database->getConnection();

$p= new DienVien ($db);

$ArrayTenDienVien = $p->layDanhSachTenDienVien();
$stmt=$p->getData();
$num=$stmt->rowCount();




foreach ($ArrayTenDienVien as $TenDienvien) {
    $stmt = $p->layThongTinPhim($TenDienvien);
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

        $mangPhims[$TenDienvien] = $phims;
    } else {
        $mangPhims[$TenDienvien] = array(); // Nếu không có phim, tạo một mảng trống
    }
}

echo json_encode($mangPhims);


?>