<?php
class phim_daodien
{
    private $conn;
    private $table_name="phim_daodien"; 
    public $MaPhim;
    public $MaDaoDien;
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
        $sql="INSERT INTO ".$this->table_name."VALUES ('". $this->MaPhim."',
         '". $this->MaDaoDien."')";
        $stmt = $this->conn->prepare($sql);
        if ($stmt->execute())
            return true;
        else          
            return false; 
    }
}
?>