<?php
$alphabets = array
(
array("a",1),
array("b",2),
array("c",3),
array("d",4),
array("e",5),
array("f",6),
array("g",7),
array("h",8),
array("i",9),
array("j",10),
array("k",11),
array("l",12),
array("i",13),
array("m",14),
array("n",15),
array("o",16),
array("p",17),
array("q",18),
array("r",19),
array("s",20),
array("t",21),
array("u",22),
array("v",23),
array("x",24),
array("y",25),
array("z",26),
);
$user= $_GET["name"];
$sum=0;
$x=0;
while($x<strlen($user)){
$ch=$user[$x];
for($y=0;$y<26;$y++)
{
if(strcmp($ch,$alphabets[$y][0])==0){
$sum+=$alphabets[$y][1];
break;
}
}
$x++;
}
$equi=($sum/(26*strlen($user))) * 10;
echo " { \"username\": ", json_encode ($user),"\n";
if($equi <1 )
$artist="Madonna";
//echo "According to our analysis, you must listen to Madonna";

else if($equi <2 )
$artist="Lady Gaga";
//echo "According to our analysis, you must listen to Lady Gaga";

else if($equi <3 )
$artist="Shakira";
//echo "According to our analysis, you must listen to Shakira";

else if($equi <4)
$artist="Justin Bieber";
//echo "According to our analysis, you must listen to Justin Bieber";

else if($equi <4.25 )
$artist="Eminem";
//echo "According to our analysis, you must listen to Eminem";

else if($equi <4.5 )
$artist="Taylor Swift";
//echo "According to our analysis, you must listen to Taylor Swift";
else if($equi <4.75 )
$artist="Usher";
//echo "According to our analysis, you must listen to Usher";
else if($equi <5 )
$artist="Justin Timberlake";
//echo "According to our analysis, you must listen to Justin Timberlake";
else if($equi <5.25 )
$artist="Beyonce";
//echo "According to our analysis, you must listen to Beyonce";
else if($equi <5.5 )
$artist="Miley Cyrus";
//echo "According to our analysis, you must listen to Miley Cyrus";
else if($equi <5.75 )
$artist="Akon";
//echo "According to our analysis, you must listen to Akon";
else if($equi <6 )
$artist="Jennifer Lopez";
//echo "According to our analysis, you must listen to Jennifer Lopez";
else if($equi <6.25 )
$artist="Selena Gomez";
//echo "According to our analysis, you must listen to Selena Gomez";
else if($equi <6.5 )
$artist="Michael Jackson";
//echo "According to our analysis, you must listen to Michael Jackson";
else if($equi <7 )
$artist="Rihanna";
//echo "According to our analysis, you must listen to Rihanna";
else if($equi <8 )
$artist="Mariah Carey";
else if($equi <9 )
$artist="Nelly";
else
$artist="Kanye West";
echo " \"artist\": ", json_encode ($artist);

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "celeb_score";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "SELECT image FROM artist_set WHERE artist= '$artist' ";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
   
echo " \"image URL\": ", json_encode ($row["image"]),"}","\n";

    }
} else {
    echo "0 results";
}
?>