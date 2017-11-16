<?php
include_once 'src/php/db_connect.php';
include_once 'src/php/functions.php';
 
sec_session_start();
?>
<!DOCTYPE html>
<html>
	<?php if (login_check($mysqli) == true) : ?>
    <head>
        <meta charset="UTF-8">
        <title>Secure Login: Protected Page</title>
        <link rel="stylesheet" href="src/styles/main.css" />
    </head>
    <body>
		<p>Currently logged as <?php echo htmlentities($_SESSION['username']); ?></p>
        <p>Welcome <?php echo htmlentities($_SESSION['username']); ?>!</p>
        <p>
            This is an example protected page.  To access this page, users
            must be logged in.  At some stage, we'll also check the role of
            the user, so pages will be able to determine the type of user
            authorised to access the page.
        </p>
        <p>If you are done, please <a href="src/php/logout.php">log out</a>.</p>
	</body>
        <?php else : ?>
			<head>
				<meta charset="UTF-8">
				<title>Doctor Dashboard: Log In</title>
				<link rel="stylesheet" href="src/styles/login.css" />
				<script type="text/JavaScript" src="src/js/sha512.js"></script> 
				<script type="text/JavaScript" src="src/js/forms.js"></script> 
			</head>
			<body>
				<?php
				if (isset($_GET['error'])) {
					echo '<p class="error">Error Logging In!</p>';
					//echo '<p class="error">'.htmlspecialchars($_GET['error']).'</p>';
				}
				?> 
				<img class="headerImg" src="src/styles/imgs/Header.png" alt="Glucose Meter Dashboard">
				<div class="loginForm">
					<form action="src/php/process_login.php" method="post" name="login_form">                      
						<p class="labels">Email: </p><input type="text" name="email" />
						<p class="labels">Password: </p><input type="password" name="password" id="password"/>
						<input type="button" value="Login" onclick="formhash(this.form, this.form.password);" /> 
					</form>
				</div>
			</body>
    <?php endif; ?>
</html>