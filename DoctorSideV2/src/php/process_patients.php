<?php
include_once 'db_connect.php';
include_once 'functions.php';
 
sec_session_start();
 
if (isset($_POST['disp_pat_json'])) {
	return get_patients($mysqli_patients);
} else {
    // The correct POST variables were not sent to this page. 
    echo 'Invalid Request';
}