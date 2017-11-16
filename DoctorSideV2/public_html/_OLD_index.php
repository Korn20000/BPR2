<?php
include_once 'src/php/db_connect.php';
include_once 'src/php/functions.php';
 
sec_session_start();
 
if (login_check($mysqli) == true) {
    $logged = 'in';
} else {
    $logged = 'out';
}
?>
<!DOCTYPE html>
<html>
    <head>
        <title>Secure Login: Log In</title>
        <link rel="stylesheet" href="src/styles/main.css" />
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
        <form action="src/php/process_login.php" method="post" name="login_form">                      
            Email: <input type="text" name="email" />
            Password: <input type="password" 
                             name="password" 
                             id="password"/>
            <input type="button" 
                   value="Login" 
                   onclick="formhash(this.form, this.form.password);" /> 
        </form>
 
<?php
        if (login_check($mysqli) == true) {
			window.open ('./main_dashboard.php','_self',false);
			//header('Location: ../../main_dashboard.php');
        } else {
			//echo '<p>Currently logged ' . $logged . '.</p>';
                }
?>      
    </body>
</html>