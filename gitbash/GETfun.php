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
$x=$_GET["id"];
if( $_GET["id"])
{
$sql = "SELECT fact FROM funfacts WHERE id= '$x' ";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
   
echo "id: ", json_encode ($_GET["id"]),"\n";
echo "fact: ", json_encode ($row["fact"]);
    }
} else {
    echo "0 results";
}
}
$conn->close();
?>






