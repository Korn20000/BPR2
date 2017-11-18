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
		<script type="text/javascript" src="src/js/view.js"></script>
		<script type="text/javascript" src="src/js/controller.js"></script>
		<script type="text/javascript" src="src/js/jquery.js"></script>
    </head>
    <body>
		<div class = "main_panel">
			<a href="src/php/logout.php">log out</a>
			<div class = "main_table">
			<p id ="main_text">Empty</p>
			<p class="loadinText">Loading patients, please wait...</p>
			<img class="loadingAnim" src="src/styles/imgs/loading.gif" alt="LOADING !">
			<a href="#" onclick="controller.clickLoadJSONTest();">load stuff</a>
			</div>
		</div>
	</body>
	<script>
    var adapter = adapter();
    var view = view();
	var model = model(adapter);
    var controller = controller(view, model);
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