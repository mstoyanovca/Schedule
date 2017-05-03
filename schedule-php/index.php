<?php
// start session:
session_start();
// initialize variables:
// empty input flags:
$missing_username = "";           // empty username
$missing_password = "";           // empty password
$login_error = "";                // wrong username and/or password
// user input storage variables:  
$username = "";                   // username
// session variables:
$_SESSION['user_id'] = "";        // id of the logged in user
$_SESSION['user_firstn'] = "";    // first name of the logged in user
$_SESSION['user_lastn'] = "";     // last name
$_SESSION['privilege'] = "";      // level of access
if(isset($_POST["submit"])) {
    include_once 'scripts/authenticate.php';
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="shortcut icon" href="images/piano.jpg" type="image/x-icon"/>
    <link rel="stylesheet" href="css/login.css"/>
    <script type="text/javascript" src="js/rss.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.1/jquery.min.js"></script>
    <title>Example Music School</title>
</head>
    
<body>
<!-- header: -->
<div class="topImage">
  <div class="topTransbox">
    <div class="topHeading">
      <h2>Example Music School</h2>
      <h2><i>Piano and music theory lessons</i></h2>
    </div>
  </div>
</div>

<!-- body: -->
<div class="center">
  <form action="" method="post">
  <div class="body">
    <h2>Login to access your schedule:</h2>
    <div class="label">
        <big>User name:</big>
    </div>
    <div class="input">
        <input name="username" type="text" class="text_box" value='<?php echo $username;?>'></input>
        <?php if($missing_username=="err") {echo "<br/><span style=color:red>Please enter your username</span>";} ?>
    </div>
    <div class="clear"></div>
    
    <div class="label">
        <big>Password:</big>
    </div>
    <div class="input">
        <input name="password" type="password" class="text_box"></input>
        <?php if($missing_password=="err") {echo "<br/><span style=color:red>Please enter your password</span>";} ?>
    </div>
    <div class="clear"></div>
 
    <p><?php if($login_error=="err") {echo "<span style=color:red>Invalid username and/or password</span>";} ?></p>
    
    <p><input type="submit" name="submit" value="Login"/></p>
    <p><a href="register.php">Register</a>
       <a href="resetPassword.php">Reset password</a></p>
    
  </div>
  </form>
    
  <div class="rss">
    <div id="select">
    <select onchange="showRSS(this.value)">
      <option value="">RSS feeds:</option>
      <option value="BBC">BBC</option>
    </select>
    </div>
    <div id="rssOutput"></div>
  </div>
</div>

<!-- footer: -->
<div class="footer">
  <div class="transbox2">
    <div class="heading2">
      Milton, Ontario, Phone: 123-123-1234, E-mail: 
      <a href="mailto:webmaster@examplemusicschool.ca?Subject=Student%20request">webmaster@examplemusicshool.ca</a>
    </div>
  </div>
</div>

</body>
</html>