<?php
class TheLoai
{
    private $conn;
    private $table_name="theloaiphim"; 
    public $MaTheLoai;
    public $TenTheLoai;
    public function __construct ($db)
    {
        $this->conn=$db;
    }
    function getData()
    {
        $sql = "SELECT * FROM " . $this->table_name; 
        $stmt = $this->conn->prepare($sql); 
        $stmt->setFetchMode (PDO:: FETCH_ASSOC); 
        $stmt->execute();
        return $stmt;
    }
    function insertData()
    {
        $sql="INSERT INTO ".$this->table_name."VALUES ('". $this->MaTheLoai."',
         '". $this->TenTheLoai."')";
        $stmt = $this->conn->prepare($sql);
        if ($stmt->execute())
            return true;
        else          
            return false; 
    }


    
    public function getPhimByTheLoai($maPhim)
    {
        $sql = "SELECT DISTINCT phim2.*
                FROM phim AS phim1
                JOIN phim_theloai AS pt1 ON phim1.MaPhim = pt1.MaPhim
                JOIN phim_theloai AS pt2 ON pt1.MaTheLoai = pt2.MaTheLoai
                JOIN phim AS phim2 ON pt2.MaPhim = phim2.MaPhim
                WHERE phim1.MaPhim = :maPhim
                AND phim2.MaPhim <> :maPhim";

        $stmt = $this->conn->prepare($sql);
        $stmt->bindParam(':maPhim', $maPhim);
        $stmt->execute();

        return $stmt;
    }



    public function countPhim()
    {
        $query = "SELECT COUNT(*) AS soLuong FROM " . $this->table_name;

        $stmt = $this->conn->prepare($query);
        $stmt->execute();

        $row = $stmt->fetch(PDO::FETCH_ASSOC);

        return $row['soLuong'];
    }
}
?>