<?php
// start session:
session_start();
// only teachers can access this page:
if($_SESSION['privilege'] != "teacher") {
    header('Location: index.php');
    exit();
}
// lesson start/end times:
require 'include/arrays.php';
// functions:
include 'include/functions.php';
// connect to the database:
require 'include/connect.php';
// error flag:
if(!isset($_SESSION['err'])) {$_SESSION['err'] = "";}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="shortcut icon" href="images/piano.jpg" type="image/x-icon"/>
<link rel="stylesheet" href="css/schedule.css"/>
<title>Schedule</title>
<link rel="stylesheet" href="css/schedule.css"/>
</head>

<body>
<h2>Welcome <?php echo $_SESSION['user_firstn']." ".$_SESSION['user_lastn']."!"; ?> </h2>
<p>The student's schedule is listed below:</p>
    
<div id="container">
    <!-- print week times column: -->
    <div name="week_times" class="left">
        <table>
            <tr class="yellow"><th>Week times</th></tr>
            <?php
            for($i=0; $i<count($week_times); $i++) {        // times
                print "<tr class=yellow><td>$week_times[$i]</td></tr>";
            }
            ?>
        </table>
    </div>
    
    <!-- print week schedule: -->
    <?php
    // print a new table for each day:
    for($j=1; $j<6; $j++) {            // days
        if($j%2==1) {
            $class = "grey_heading";   // grey background
        } else {
            $class = "lavender_heading";  // white background
        }
        echo "<div class=left><table style=width:150px><tr class=".$class."><th>".getDay($j)."</th></tr>";
        $skip = 0;  // don't print empty cell indisde a lesson
        for($i=0; $i<count($week_times); $i++) {      // times
            // get command:
            $command = "SELECT * FROM ".getDay($j)." WHERE time_from='$i';";
            // execute query:
            $result = $conn->query($command);
            // populate the table:
            if($data = mysqli_fetch_object($result)) {
                $skip = $data->units-1;
                if($data->units==2) {
                    print "<tr class=taken102><td>".$data->first_name." ".$data->last_name."<br/>";
                }
                if($data->units==3) {
                    print "<tr class=taken154><td>".$data->first_name." ".$data->last_name."<br/>";
                }
                if($data->units==4) {
                    print "<tr class=taken206><td>".$data->first_name." ".$data->last_name."<br/>";
                }
                if($data->units==6) {
                    print "<tr class=taken310><td>".$data->first_name." ".$data->last_name."<br/>";
                }
                if(!empty($data->home_phone)) {print $data->home_phone." home<br/>";}
                if(!empty($data->cell_phone)) {print $data->cell_phone." cell<br/>";}
                if(!empty($data->work_phone)) {print $data->work_phone." work<br/>";}    
                print "</td></tr>";
            } elseif($skip==0) {
                print "<tr class=".$class."><td></td></tr>";
            } elseif($skip>0) {
                $skip--;
            }
        }
        echo "</table></div>";
    }
    ?>
    
    <!-- print the first part of the Saturday times column: -->
    <div name="sat_times1" class="left">
        <table border="1">
            <tr class="yellow"><th>Sat times</th></tr>
            <?php
            for($i=0; $i<count($week_times); $i++) {        // times
                print "<tr class=yellow><td>$sat_times[$i]</td></tr>";
            }
            ?>
        </table>
    </div>
    
    <!-- print the first part of the Saturday schedule: -->
    <?php
    echo "<div class=left><table style=width:150px><tr class=grey_heading><th>Saturday</th></tr>";
    $skip = 0;  // don't print empty cell indisde a lesson
    for($i=0; $i<count($week_times); $i++) {      // times
        // get command:
        $command = "SELECT * FROM saturday WHERE time_from='$i';";
        // execute query:
        $result = $conn->query($command);
        // populate the table:
        if($data = mysqli_fetch_object($result)) {
            $skip = $data->units-1;
            if($data->units==2) {
                print "<tr class=taken102><td>".$data->first_name." ".$data->last_name."<br/>";
            }
            if($data->units==3) {
                print "<tr class=taken154><td>".$data->first_name." ".$data->last_name."<br/>";
            }
            if($data->units==4) {
                print "<tr class=taken206><td>".$data->first_name." ".$data->last_name."<br/>";
            }
            if($data->units==6) {
                print "<tr class=taken310><td>".$data->first_name." ".$data->last_name."<br/>";
            }
            if(!empty($data->home_phone)) {print $data->home_phone." home<br/>";}
            if(!empty($data->cell_phone)) {print $data->cell_phone." cell<br/>";}
            if(!empty($data->work_phone)) {print $data->work_phone." work<br/>";}    
            print "</td></tr>";
        } elseif($skip==0) {
            print "<tr class=grey_heading><td></td></tr>";
        } elseif($skip>0) {
            $skip--;
        }
    }
    echo "</table></div>";
    ?>
    
    <!-- print the second part of the Saturday times column: -->
    <div name="sat_times2" class="left">
        <table>
            <tr class=yellow><th>Sat times</th></tr>
            <?php
            for($i=count($week_times)+1; $i<count($sat_times); $i++) {        // times
                print "<tr class=yellow><td>$sat_times[$i]</td></tr>";
            }
            ?>
        </table>
    </div>
    
    <!-- print the second part of the Saturday schedule: -->
    <?php
    echo "<div class=left><table style=width:150px><tr class=lavender_heading><th>Saturday</th></tr>";
    $skip = 0;  // don't print empty cell indisde a lesson
    for($i=count($week_times)+1; $i<count($sat_times); $i++) {      // times
        // get command:
        $command = "SELECT * FROM saturday WHERE time_from='$i';";
        // execute query:
        $result = $conn->query($command);
        // populate the table:
        if($data = mysqli_fetch_object($result)) {
            $skip = $data->units-1;
            if($data->units==2) {
                print "<tr class=taken102><td>".$data->first_name." ".$data->last_name."<br/>";
            }
            if($data->units==3) {
                print "<tr class=taken154><td>".$data->first_name." ".$data->last_name."<br/>";
            }
            if($data->units==4) {
                print "<tr class=taken206><td>".$data->first_name." ".$data->last_name."<br/>";
            }
            if($data->units==6) {
                print "<tr class=310><td>".$data->first_name." ".$data->last_name."<br/>";
            }
            if(!empty($data->home_phone)) {print $data->home_phone." home<br/>";}
            if(!empty($data->cell_phone)) {print $data->cell_phone." cell<br/>";}
            if(!empty($data->work_phone)) {print $data->work_phone." work<br/>";}    
            print "</td></tr>";
        } elseif($skip==0) {
            print "<tr class=lavender_heading><td></td></tr>";
        } elseif($skip>0) {
            $skip--;
        }
    }
    echo "</table></div>";
    // close the connection:
    $conn->close();
    ?>
</div>

<!-- links: -->
<div style="clear:both">
    <p></br>
    <?php if($_SESSION['err']=="err") {echo "<span style=color:red>Time taken</span>";} ?>
        <a href="addEntry.php">Add</a>
        <a href="removeEntry.php">Remove</a>
        <a href="students.php">Students</a>
        <a href="changePW.php">Change password</a>
        <a href="include/logout.php">Logout</a>
    </p>
</div>

</body>
</html>