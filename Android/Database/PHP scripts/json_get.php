<?php

	$host = "localhost";
	$user = "id3343620_bachelor";
	$password = "0123456789";
	$db = "id3343620_bachelor";
	
	$sql = "select * from Patient;";
	
	$con = mysqli_connect($host, $user, $password, $db);
	
	$result = mysqli_query($con,$sql);
	
	$response = array();
	
	while($row = mysqli_fetch_array($result))
	{
		array_push($response, array("CPR"=>$row[0], "Name"=>$row[1], "Surname"=>$row[2], "Age"=>$row[3], 
									"Type"=>$row[4], "Phone"=>$row[5], "Address"=>$row[6], "Email"=>$row[7],)
	}
	
	echo json_encode(array("server_response"=>$response));
	
	mysqli_close($con);
?>