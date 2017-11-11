<?php           

header('Constant-Type: application/json');

$conn = mysqli_connect("localhost", "id3343620_bachelor", "12345", "id3343620_bachelor");

if(!$mysqli)
{
	die("Connection failer: " . $mysqli->error);
}

$query = sprintf("SELECT Time, Level FROM PatientHistory ORDER BY Time");

$result = $mysqli->query($query);

$data = array();
foreach ($result as $row)
{
	$data[] = $row;
}

$result->close();

$mysqli->close();

print json_encode($data);
?>
