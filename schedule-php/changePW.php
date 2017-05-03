<?php
    // start session:
    session_start();
    // only logged in users can access this page:
    if($_SESSION['privilege'] != ("student"||"teacher")) {
        header('Location: index.php');
        exit();
    }
    include 'include/functions.php';
    // change password:
    if(isset($_POST["submit"])) {
        $err = "";
        if(empty($_POST['password_new1'])||empty($_POST['password_new2'])) {
            $err = "Please fill all fields";
        } elseif($_POST['password_new1']!=$_POST['password_new2']) {
            $err = "Your new passwords don't match";
        } else {
            if(validate_password($_POST['password_new1'])) {
                include_once 'classes/User.php';
                $user = new user($_SESSION['user_firstn'], $_SESSION['user_lastn'], "", "", $_POST['password_new1'], 
                                 $_SESSION['privilege'], "", "");
                $user->change_pw($_SESSION['user_id']);
            } else {
                $err = "Invalid password: number, small, capital letter, min length 8";
            }
        }
    }
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="shortcut icon" href="images/piano.jpg" type="image/x-icon"/>
<link rel="stylesheet" href="css/entry.css"/>
<title>Change password</title>
</head>

<body>
<form action="" method="POST">
    <h2> User: <?php echo $_SESSION['user_firstn']." ".$_SESSION['user_lastn']; ?> </h2>
    <p>Change password:</p>

    <div class="container">
        <div class="label">
            New password:
        </div>
        <div class="left">
            <input type="password" name="password_new1"/><br/>
        </div>    
    
        <div class="clear"></div>
    
        <div class="label">
            Confirm new password:
        </div>
        <div class="left">
            <input type="password" name="password_new2"/>
            <?php if(!empty($err)) {echo "<span style=color:red>".$err."</span>"; } ?>
        </div>
    
        <div class="clear"></div>
    </div>
    
    <p><input name="submit" type="submit" value="Change"/>
       <?php if($_SESSION['privilege']=="student") {echo "<a href='studentSchedule.php'>Cancel</a>";}
             elseif($_SESSION['privilege']=="teacher") {echo "<a href='schedule.php'>Cancel</a>";}?></p>
</form>
</body>
</html>