<?php
header ("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
include '../Connect/database.php';
include 'danhmuc.php';
$database=new Database(); 
$db=$database->getConnection();
$p= new danhmuc ($db);


// Lấy danh sách toàn bộ maDanhMuc
$allMaDanhMuc = array(1, 2, 3, 4); // Thay thế bằng mã danh mục thực tế từ cơ sở dữ liệu của bạn

$mangPhims = array();

foreach ($allMaDanhMuc as $maDanhMuc) {
    $stmt = $p->getPhimByMaDanhMuc($maDanhMuc);
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

        $mangPhims[$maDanhMuc] = $phims;
    } else {
        $mangPhims[$maDanhMuc] = array(); // Nếu không có phim, tạo một mảng trống
    }
}

echo json_encode($mangPhims);


?>