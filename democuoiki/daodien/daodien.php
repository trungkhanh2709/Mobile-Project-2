<?php
class DaoDien
{
    private $conn;
    private $table_name="daodien"; 
    public $MaDaoDien;
    public $TenDaoDien;
    public $QuocTich;
    public $NgayThangNamSinh;
    public $UrlHinhAnh;
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
        $sql="INSERT INTO ".$this->table_name."VALUES ('". $this->MaDaoDien."',
         '". $this->TenDaoDien."','". $this->QuocTich."','". $this->NgayThangNamSinh."','". $this->UrlHinhAnh."')";
        $stmt = $this->conn->prepare($sql);
        if ($stmt->execute())
            return true;
        else          
            return false; 
    }
}
?>