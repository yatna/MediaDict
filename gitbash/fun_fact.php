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
$x=mt_rand(1,90);
$sql = "SELECT id,fact FROM funfacts WHERE id= '$x'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
   
echo "id: ", json_encode ($row["id"]),"\n";
echo "fact: ", json_encode ($row["fact"]);
    }
} else {
    echo "0 results";
}
$conn->close();
?>