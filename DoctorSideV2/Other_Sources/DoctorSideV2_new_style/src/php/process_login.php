<?php
include_once 'db_connect.php';
include_once 'functions.php';
 
sec_session_start(); // Our custom secure way of starting a PHP session.
 
if (isset($_POST['email'], $_POST['p'])) {
    $email = $_POST['email'];
    $password = $_POST['p']; // The hashed password.
 
	$login_result = login($email, $password, $mysqli_doctors);
    if ($login_result == 'ok') {
        // Login success 
        header('Location: ../../index.php');
    } else {
        // Login failed 
        header('Location: ../../index.php?status='.$login_result);
    }
} else {
    // The correct POST variables were not sent to this page. 
    echo 'Invalid Request';
}