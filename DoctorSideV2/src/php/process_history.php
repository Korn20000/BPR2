<?php
include_once 'db_connect.php';
include_once 'functions.php';

sec_session_start();
if (isset($_POST['CPRNo']) && $_POST['actionId'] == 'disp_pat_history_json') {
//if (isset($_POST['value']) && $_POST['name'] == 'disp_pat_history_json') {
	$cpr = $_POST['CPRNo'];
//	echo '<script>console.log("about to json_encode get_patients_history")</script>';
	echo json_encode(get_patients_history($mysqli_patients, $cpr));
}
else {
	// The correct POST variables were not sent to this page.
	echo 'Invalid Request';
}