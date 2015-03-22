<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "fun";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "INSERT INTO funfacts (fact)
VALUES ('The computer in Iron Man ,J.A.R.V.I.S is an acronym for Just A Rather Very Intelligent System')";

if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

?>