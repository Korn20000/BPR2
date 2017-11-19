<?php
include_once 'src/php/db_connect.php';
include_once 'src/php/functions.php';

sec_session_start();
?>
<!DOCTYPE html>
<html>
	<?php if (login_check($mysqli_doctors) == true) : ?>
    <head>
        <meta charset="UTF-8">
        <title>Main Dashboard</title>
        <link rel="stylesheet" href="src/styles/main.css" />
		<script type="text/javascript" src="src/js/adapter.js"></script>
		<script type="text/javascript" src="src/js/model.js"></script>
		<script type="text/javascript" src="src/js/modelmanager.js"></script>
		<script type="text/javascript" src="src/js/view.js"></script>
		<script type="text/javascript" src="src/js/controller.js"></script>
		<script type="text/javascript" src="src/js/jquery.js"></script>
		<script type="text/javascript" src="src/js/babel.js"></script>
    </head>
    <body>
		<div class = "main_panel">
		<form id="searchSelector">
			<input type="radio" name="searchBy" value="1" checked> CPR<br>
			<input type="radio" name="searchBy" value="2"> First name<br>
			<input type="radio" name="searchBy" value="3"> Last name
			<input type="radio" name="searchBy" value="4"> Age<br>
			<input type="radio" name="searchBy" value="5"> Diabetes type<br>
			<input type="radio" name="searchBy" value="6"> Phone<br>
			<input type="radio" name="searchBy" value="7"> Address<br>
			<input type="radio" name="searchBy" value="8"> Email
		</form>
			<input type="text" id="searchInput" onkeyup="view.search()" placeholder="Search...">
			<a href="src/php/logout.php">log out</a>
			<a href="#" onclick="controller.getPatientsFromModelManager();">load stuff</a>
			<div id="loadingList">
				<p class="loadinText">Loading patients, please wait...</p>
				<img class="loadingAnim" src="src/styles/imgs/loading.gif" alt="LOADING !">
			</div>
			<div id = "main_table">
			</div>
		</div>
	</body>
	<script>
    var adapter = adapter();
    var view = view();
	var model = model();
	var modelManager = modelmanager(model, adapter);
    var controller = controller(view, modelManager);
		window.onload = controller.getPatientsFromModelManager();
	</script>
    <?php else : ?>
    <head>
        <meta charset="UTF-8">
        <title>Redirecting</title>
        <link rel="stylesheet" href="src/styles/redirect.css" />
		<meta http-equiv="refresh" content="5;url=./index.php" />
    </head>
    <body>
		<div class = "main">
			<div class = "wrapper">
				<p class="loggedAsText">Access denied !<br>Please log in first !</p>
				<p class="redirectText">Redirecting to Log in page in 5 seconds...</p>
				<img class="loadingAnim" src="src/styles/imgs/loading.gif" alt="Please Wait...">
			</div>
		</div>
	</body>
    <?php endif; ?>
</html>