<?php

	require_once "Constants.php";
	
	$con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD);
	
	if(!$con)
	{
		echo "Database connection failed";
	}
	else
	{
		echo "Database connection OK";
	}
	
?>



