<?php
class danhmuc
{
    private $conn;
    private $table_name="danhmuc"; 
    public $MaDanhMuc;
    public $TenDanhMuc;
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
        $sql="INSERT INTO ".$this->table_name."VALUES ('". $this->MaDanhMuc."',
         '". $this->TenDanhMuc."')";
        $stmt = $this->conn->prepare($sql);
        if ($stmt->execute())
            return true;
        else          
            return false; 
    }
        public function getDanhMucVaPhim() {
        $query = "SELECT dm.MaDanhMuc, dm.TenDanhMuc, pd.MaPhim
                  FROM $this->table_name dm
                  INNER JOIN phim_danhmuc pd ON dm.MaDanhMuc = pd.MaDanhMuc";

        $stmt = $this->conn->prepare($query);
        $stmt->execute();

        return $stmt;
    }

    public function getPhimByMaDanhMuc($maDanhMuc) {
        $query = "SELECT p.*
                  FROM phim p
                  INNER JOIN phim_danhmuc pd ON p.MaPhim = pd.MaPhim
                  WHERE pd.MaDanhMuc = :maDanhMuc";

        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(":maDanhMuc", $maDanhMuc);
        $stmt->execute();

        return $stmt;
    }

}
?>