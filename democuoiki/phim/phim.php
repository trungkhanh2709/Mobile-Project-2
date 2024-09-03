<?php
class Phim
{
    private $conn;
    private $table_name = "phim"; 
    public $MaPhim;
    public $TenPhim;
    public $NamRaMat;
    public $QuocGia;
    public $MoTaPhim;
    public $ThoiLuongPhim;
    public $URLTrailer;
    public $PosterPhim;

    public function __construct($db)
    {
        $this->conn = $db;
    }

    public function getData()
    {
        $sql = "SELECT * FROM " . $this->table_name; 
        $stmt = $this->conn->prepare($sql); 
        $stmt->setFetchMode(PDO::FETCH_ASSOC); 
        $stmt->execute();
        return $stmt;
    }

    public function insertData()
    {
       
        $sql = "INSERT INTO " . $this->table_name ."VALUES ('". $this->TenPhim."',
         '". $this->NamRaMat."','". $this->QuocGia."','". $this->MoTaPhim."','". $this->ThoiLuongPhim."','". $this->URLTrailer."','". $this->PosterPhim."')";
        $stmt = $this->conn->prepare($sql);
        
        if ($stmt->execute()) {
            return true;
        } else {
            return false; 
        }
    }
}
?>