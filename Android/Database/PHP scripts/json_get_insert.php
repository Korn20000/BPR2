<?php

	$host = "localhost";
	$user = "id3343620_bachelor";
	$password = "12345";
	$db = "id3343620_bachelor";
	
	$sql = "select * from Patient;";
	
	$con = mysqli_connect($host, $user, $password, $db);
	
	$result = mysqli_query($con,$sql);
	
	$response = array();
	
	while($row = mysqli_fetch_array($result))
	{
		array_push($response,array("CPR"=>$row[0],"Name"=>$row[1],"Surname"=>$row[2],"Age"=>$row[3],"Type" =>$row[4],"Phone"=>$row[5],"Address"=>$row[6],"Email"=>$row[7]));
	}
	
	echo json_encode(array('server_response'=>$response));
	
	if($_SERVER["REQUEST_METHOD"]=="POST")
	{
		insertData();
	}
	
	function insertData()
	{
		global $con;
		
		$CPR = $_POST["CPR"];
		$Date = $_POST["Date"];
		$Level = $_POST["Level"];
		
		$query = " INSERT INTO History(CPR, Date, Level) values ('$CPR', '$Date', '$Level');";
		
		mysqli_query($con, $query) or die (mysqli_error($con));
	}
	
	mysqli_close($con);

?>




