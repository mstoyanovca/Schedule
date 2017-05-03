<?php
// start session:
session_start();
// only teachers can access this page:
if($_SESSION['privilege'] != "teacher") {
    header('Location: index.php');
    exit();
}
// include the Entry.class:
require_once 'classes/Entry.php';
if(isset($_POST["submit"])) {
    $entry = new Entry($_POST['time_from'], "", $_POST['day'], "");
    $entry->remove();
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="shortcut icon" href="images/piano.jpg" type="image/x-icon"/>
<link rel="stylesheet" href="css/entry.css"/>
<title>Remove entry</title>
<script type="text/javascript" src="js/removeEntry.js">
</script>
</head>

<body>
<form action="" method="POST">
<h2>Teacher: <?php echo $_SESSION['user_firstn']." ".$_SESSION['user_lastn']; ?> </h2>
<p>Remove entry:</p>

<div class="container">
    <!-- select lesson day: -->
    <div class="label">
        Day:
    </div>
    <div class="left">
        <select name="day" class="select" onchange="enableBtn1(this.value)">
            <option value="">Select day:</option>
            <option value="1">Monday</option>
            <option value="2">Tuesday</option>
            <option value="3">Wednesday</option>
            <option value="4">Thursday</option>
            <option value="5">Friday</option>
            <option value="6">Saturday</option>
        </select>
    </div>
    <div class="clear"></div>
    <!-- select lesson start time: -->
    <div class="label">
        Start time: 
    </div>
    <div class="left">
        <select name="time_from" id="time_from" class="select" onchange="enableBtn2(this.value)" disabled=true>
            <option value="">Select time:</option>
        </select>
    </div>
    <div class="clear"></div>
</div>

<!-- submit the form: -->
<p><input name="submit" type="submit" id="btn" value="Remove" disabled=true/>
   <a href="schedule.php">Cancel</a></p>
</form>
</body>
</html>