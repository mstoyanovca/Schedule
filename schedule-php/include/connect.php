<?php
// MySQL server login constants:
include 'config.php';

// creates new connection:
$conn = new mysqli($hostname, $serverUsername, $serverPassword, $dbname);

// check connection:
if ($conn->connect_error) {
    die('Connect Error (' . $conn->connect_errno . ') '. $conn->connect_error);
}
?>