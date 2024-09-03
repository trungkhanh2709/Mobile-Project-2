<?php
require "../dsxemsau/dsxemsau.php";

class NguoiDung {
    private $conn;
    private $table_name = "nguoidung"; 
    public $TenNguoiDung;
    public $TaiKhoan;
    public $MatKhau;
    // public $MaNguoiDung;

    public function __construct($db) {
        $this->conn = $db;
    }

    public function setData() {
        $nguoiDung = new NguoiDung($this->conn);
        $nguoiDung->insertData();
    }

    public function getData() {
        $sql = "SELECT * FROM " . $this->table_name; 
        $stmt = $this->conn->prepare($sql); 
        $stmt->setFetchMode(PDO::FETCH_ASSOC); 
        $stmt->execute();
        return $stmt;
    }

    public function insertData() {
        $sql = "INSERT INTO ".$this->table_name." (TenNguoiDung, TaiKhoan, MatKhau) VALUES (:TenNguoiDung, :TaiKhoan, :MatKhau)";
        $stmt = $this->conn->prepare($sql);
        $stmt->bindParam(':TenNguoiDung', $this->TenNguoiDung);
        $stmt->bindParam(':TaiKhoan', $this->TaiKhoan);
        $stmt->bindParam(':MatKhau', $this->MatKhau);
    
        if (!$stmt->execute()) {
            printf("Error: %s.\n", $stmt->error);
            return false;
        }
        
        return true;
    }
    
    

    // Các phương thức và công việc khác của class...
}
    
    

    
    // public function findMaNguoiDung($TenNguoiDung, $MatKhau) {
    //     $query = "SELECT nguoidung.MaNguoiDung 
    //               FROM nguoidung 
    //               INNER JOIN danhsachxemsau ON nguoidung.MaNguoiDung = dsxemsau.MaNguoiDung
    //               WHERE nguoidung.TenNguoiDung = :TenNguoiDung AND nguoidung.MatKhau = :MatKhau";
    
    //     $stmt = $this->conn->prepare($query);
    //     $stmt->bindParam(':TenNguoiDung', $TenNguoiDung);
    //     $stmt->bindParam(':MatKhau', $MatKhau);
    //     $stmt->execute();
    
    //     // Kiểm tra xem có kết quả trả về hay không
    //     if ($stmt->rowCount() > 0) {
    //         $row = $stmt->fetch(PDO::FETCH_ASSOC);
    //         return $row['MaNguoiDung']; // Trả về MaNguoiDung nếu tìm thấy
    //     } else {
    //         return false; // Trả về false nếu không tìm thấy
            
    //     }
    // }
    // function updateData() {
    //     $sql = "UPDATE " . $this->table_name . " SET MatKhau = :MatKhau WHERE MaNguoiDung = :MaNguoiDung";
    //     $stmt = $this->conn->prepare($sql);
    //     $stmt->bindParam(':MatKhau', $this->MatKhau);
    //     $stmt->bindParam(':MaNguoiDung', $this->MaNguoiDung);
    //     if ($stmt->execute()) {
    //         return true;
    //     } else {
    //         return false;
    //     }
    // }
    


?>
