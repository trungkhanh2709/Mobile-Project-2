<?php
class hinhanhphim
{
    private $conn;
    private $table_name="hinhanhphim"; 
    public $MaHinhAnh;
    public $UrlHinhAnh;
    public $MaPhim;
    
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
        $sql="INSERT INTO ".$this->table_name."VALUES ('". $this->MaHinhAnh."',
         '". $this->MaPhim."','". $this->UrlHinhAnh."')";
        $stmt = $this->conn->prepare($sql);
        if ($stmt->execute())
            return true;
        else          
            return false; 
    }
}
?>