<?php
// start session:
session_start();
// only teachers can access this page:
if($_SESSION['privilege'] != "teacher") {
    header('Location: index.php');
    exit();
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="shortcut icon" href="images/piano.jpg" type="image/x-icon"/>
<link rel="stylesheet" href="css/students.css"/>
<title>Example Music School</title>
</head>

<body>
<h2>Welcome <?php echo $_SESSION['user_firstn']." ".$_SESSION['user_lastn']."!"; ?> </h2>
<p>The students' table is listed below:</p>

<!-- displays the students in an html table: -->
<table>
<tr class="heading"><th>First Name</th><th>Last Name</th><th>Home Phone</th><th>Cell Phone</th><th>Work Phone</th></tr>

<?php
// connect to the database:
require_once 'include/connect.php';
// execute query:
$command = "SELECT * FROM students ORDER BY first_name, last_name;";
$result = $conn->query($command);
$i = 1;
// populate the table:
while($data = mysqli_fetch_object($result)) {
    if($i%2) {
        $class = "grey";
    } else  {
        $class = "white";
    }
    print "<tr class=".$class."><td>".$data->first_name."</td><td>".$data->last_name."</td><td>".$data->home_phone."</td>
           <td>".$data->cell_phone."</td><td>".$data->work_phone."</td></tr>";
    $i++;
}
// close the connection:
$conn->close();
?>
</table>

<!-- links: -->
<br/>
<a href="addStudent.php" style="background: silver">Add</a>
<a href="editStudent.php">Edit</a>
<a href="removeStudent.php">Remove</a>
<a href="schedule.php">Schedule</a>
<a href="include/logout.php">Logout</a>
</body>
</html>