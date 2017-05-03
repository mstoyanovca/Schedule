<?php
// start session:
session_start();
// only teachers can access this page:
if($_SESSION['privilege'] != "teacher") {
    header('Location: index.php');
    exit();
}
if(isset($_POST["submit"])) {
    require_once 'classes/Student.php';
    $student = new Student("", "", "", "", "");
    $student->remove($_POST['student']);
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="shortcut icon" href="images/piano.jpg" type="image/x-icon"/>
<link rel="stylesheet" href="css/entry.css"/>
<title>Remove student</title>
<script type="text/javascript">
// enables the remove button only when a name is selected:
function enableBtn(str) {
    if (str=="") {
        document.getElementById("btn").disabled = true;
        return;
    }
    else {
        document.getElementById("btn").disabled = false;
        return;
    }   
}
</script>
</head>

<body>
<form action="" method="POST">
<h2>Teacher: <?php echo $_SESSION['user_firstn']." ".$_SESSION['user_lastn']; ?></h2>
<p>Remove a student:</p>

<div class="container">
    <div class="label">
        Select a student:
    </div>
    <div class="left">
        <select name="student" id="student" onchange="enableBtn(this.value)">
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
</div>
        
<p><input name="submit" type="submit" id="btn" value="Remove" disabled=true/>
   <a href="students.php">Cancel</a></p>
</form>
</body>
</html>