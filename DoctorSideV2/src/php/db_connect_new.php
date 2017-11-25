<?php
include_once 'psl-config.php';   // As functions.php is not included
$mysqli_doctors = new mysqli(HOST, USER_DOC, PASSWORD_DOC, DATABASE_DOC);
$mysqli_patients = new mysqli(HOST, USER_PAT, PASSWORD_PAT, DATABASE_PAT);