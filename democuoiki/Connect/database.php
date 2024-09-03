<?php
class Database
{
    private $host = "localhost";
    private $db_name = "riviu_database";
    private $user = "root";
    private $pass = "";
    public $conn;

    public function getConnection()
    {
        $this->conn = null;
        try {
            $this->conn = new PDO("mysql:host=" . $this->host . ";dbname=" . $this->db_name, $this->user, $this->pass);
            $this->conn->exec("set names utf8");
        } catch (PDOException $e) {
            echo "Connection error" . $e->getMessage();
        }
        return $this->conn;
    }
}

?>