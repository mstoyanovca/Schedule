<?php
  // start session:
  session_start();
  // only students can access this page:
  if($_SESSION['privilege'] != "student") {
      header('Location: index.php');
      exit();
  }
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html" charset=utf-8"/>
<title>Student's schedule</title>
</head>

<body>
    <h2> Welcome <?php echo $_SESSION['user_firstn']." ".$_SESSION['user_lastn']."!"; ?> </h2>
    <p>Your schedule is listed below:</p>

<?php
include_once 'classes/Student.php';
$student = new Student($_SESSION['user_firstn'], $_SESSION['user_lastn'], "", "", "");
$student->displaySchedule();
?>

<!-- links: -->
<p><a href="changePW.php">Change password</a> | <a href="include/logout.php">Logout</a></p>
</body>
</html>