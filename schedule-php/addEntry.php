<?php
// start session:
session_start();
// only teachers can access this page:
if($_SESSION['privilege'] != "teacher") {
    header('Location: index.php');
    exit();
}
// arrays of possible lesson start/end times:
require_once 'include/arrays.php';
// taken time flag:
$_SESSION['err'] = "";
if(isset($_POST["submit"])) {
    require_once 'classes/Entry.php';
    // instantiate the Entry class:
    $entry = new Entry($_POST['time_from'], $_POST['time_to'], $_POST['day'], $_POST['student']);
    $entry->add();
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="shortcut icon" href="images/piano.jpg" type="image/x-icon"/>
<link rel="stylesheet" href="css/entry.css"/>
<title>Add entry</title>
<script type="text/javascript" src="js/addEntry.js">
</script>
</head>

<body>
<form action="" method="POST">
<h2>Teacher: <?php echo $_SESSION['user_firstn']." ".$_SESSION['user_lastn']; ?> </h2>
<p>Add entry:</p>

<!-- select student: -->
<div class="container">
<div class="label">
    Student: 
</div>
<div class="left">
    <select name="student" class="select" onchange="enableBtn1(this.value)">
        <option value="">Select a student:</option>
        <?php
        // connect to the database:
        require_once 'include/connect.php';
        // execute query:
        $command = "SELECT id, first_name, last_name FROM students ORDER BY first_name, last_name;";
        $result = $conn->query($command);
        // populate the select control:
        while($data = mysqli_fetch_object($result)) {
            print "<option value=$data->id>$data->first_name $data->last_name</option>";
        }
        // close the connection:
        $conn->close();
        ?>
     </select>
</div>

<div class="clear"></div>

<!-- select lesson day: -->
<div class="label">
    Day
</div>
<div class="left">
    <select name="day" class="select" onchange="enableBtn2(this.value)">
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
    <select name="time_from" id="time_from" class="select" onchange="enableBtn3(this.value)" disabled=true>
        <option value="">Select time:</option>
    </select>
</div><br/>

<div class="clear"></div>

<!-- select lesson end time: -->
<div class="label">
    End time:
</div>
<div class="left">
    <select name="time_to" id="time_to" class="select" onchange="enableBtn4(this.value)" disabled=true>
        <option value="">Select time:</option>
    </select>
</div>

<div class="clear"></div>
</div>

<!-- submit the form: -->
<p><input name="submit" type="submit" id="btn" value="Add" disabled=true/>
   <a href="schedule.php">Cancel</a></p>
</form>
</body>
</html>