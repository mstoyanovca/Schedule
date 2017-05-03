<?php
// start session:
session_start();
// only teachers can access this page:
if($_SESSION['privilege'] != "teacher") {
    header('Location: index.php');
    exit();
}

$q=$_GET["q"];

// connect to the database:
require_once '../include/connect.php';
// execute query:
$command = "SELECT * FROM students WHERE id='".$q."';";
$result = $conn->query($command);
// populate the input controls:
while($data = mysqli_fetch_object($result)) {
    echo "<div class=label>
              First Name:
          </div>
          <div class=left>
              <input type=text name=first_name value='$data->first_name'/>
          </div>
          <div class=clear>
          <div class=label>
              Last Name:
          </div>
          <div class=left>
              <input type=text name=last_name value='$data->last_name'/>
          </div>
          <div class=clear>
          <div class=label>
              Home Phone:
          </div>
          <div class=left>
              <input type=text name=home_phone value='$data->home_phone' />
          </div>
          <div class=clear>
          <div class=label>
              Cell Phone:
          </div>
          <div class=left>
              <input type=text name=cell_phone value='$data->cell_phone'/>
          </div>
          <div class=clear>
          <div class=label>
              Work Phone:
          </div>
          <div class=left>
              <input type=text name=work_phone value='$data->work_phone' />
          </div>
          <div class=clear>";
}
// close the connection:
$conn->close();
?>