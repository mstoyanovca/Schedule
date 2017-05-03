<?php
// start session:
session_start();
// only teachers can access this page:
if($_SESSION['privilege'] != "teacher") {
    header('Location: index.php');
    exit();
}
if(isset($_POST["submit"])) {
    include_once 'classes/Student.php';
    $student = new Student($_POST[first_name], $_POST[last_name], $_POST[home_phone], $_POST[cell_phone], $_POST[work_phone]);
    $student->add();
    // return to the student's table:
    header("Location: students.php");
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="shortcut icon" href="images/piano.jpg" type="image/x-icon"/>
<link rel="stylesheet" href="css/entry.css"/>
<title>Add student</title>
</head>

<body>
<form action="" method="POST">
<h2>Teacher: <?php echo $_SESSION['user_firstn']." ".$_SESSION['user_lastn']; ?> </h2>
<p>Add a new student:</p>

<div class="container">
    <div class="label">
        First Name:
    </div>
    <div class="left">
        <input type="text" name="first_name"/>
    </div>
    <div class="clear"></div>
    <div class="label">
        Last Name:
    </div>
    <div class="left">
        <input type="text" name="last_name"/>
    </div>
    <div class="clear"></div>
    <div class="label">
        Home phone:
    </div>
    <div class="left">
        <input type="text" name="home_phone"/><br/>
    </div>
    <div class="clear"></div>
    <div class="label">
        Cell Phone:
    </div>
    <div class="left">
        <input type="text" name="cell_phone"/><br/>
    </div>
    <div class="clear"></div>
    <div class="label">
        Work Phone:
    </div>
    <div class="left">
        <input type="text" name="work_phone"/><br/>
    </div>
    <div class="clear"></div>
</div>

<p><input name="submit" type="submit" value="Add"/>
   <a href="students.php">Cancel</a></p>
</form>
</body>
</html>