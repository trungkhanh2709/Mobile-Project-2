<?php
class DanhGia
{
    private $conn;
    private $table_name="danhgia"; 
    public $MaDanhGia;
    public $MaPhim;
    public $MaNguoiDung;
    public $DiemSo;
    public $BinhLuan;
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
    function insertData() {
        $sql = "INSERT INTO " . $this->table_name . " (MaDanhGia, MaPhim, MaNguoiDung, DiemSo, BinhLuan) 
                VALUES (:MaDanhGia, :MaPhim, :MaNguoiDung, :DiemSo, :BinhLuan)";
        
        $stmt = $this->conn->prepare($sql);
    
        $stmt->bindParam(':MaDanhGia', $this->MaDanhGia);
        $stmt->bindParam(':MaPhim', $this->MaPhim);
        $stmt->bindParam(':MaNguoiDung', $this->MaNguoiDung);
        $stmt->bindParam(':DiemSo', $this->DiemSo);
        $stmt->bindParam(':BinhLuan', $this->BinhLuan);
    
        if ($stmt->execute()) {
            return true;
        } else {
            return false;
        }
    }
    
}
?>