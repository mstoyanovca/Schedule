<?php
// check for missing username or password input:
if(empty($_POST['username'])) {
    $missing_username = "err";
} else {
    // preserve username into the form, sanitize:
    $username = filter_var($_POST['username'], FILTER_SANITIZE_STRING);
}
if(empty($_POST['password'])) {
    $missing_password = "err";
}
// input is complete, check for existing user:
if(empty($missing_username)&&empty($missing_password)) {
    // connect to the MySQL server:
    require_once 'include/connect.php';
    // use prepared SQL statements against SQL injection:
    $stmt = $conn->prepare("SELECT user_id, first_name, last_name, privilege FROM users WHERE username=? AND password=?
                            LIMIT 1;");
    // bind the inputs of the prepared statement:
    $_POST['password'] = md5($_POST['password']);
    $stmt->bind_param("ss", $_POST['username'], $_POST['password']);
    // bind the output of the prepared statement:
    $stmt->bind_result($col1, $col2, $col3, $col4);
    // execute the prepared statement:
    $stmt->execute();
    // Check for username and password match:
    if($stmt->fetch()) {
        // Login is successful; set the session variables:
        $_SESSION['user_id'] = $col1;
        $_SESSION['user_firstn'] = $col2;
        $_SESSION['user_lastn'] = $col3;
        $_SESSION['privilege'] = $col4;                // level of access
        // close the connection:
        $stmt->close();
        $conn->close();
        if($_SESSION['privilege']=="teacher") {        // teachers access the whole schedule and can edit it
            header("Location: schedule.php");
            exit();
        } elseif($_SESSION['privilege']=="student") {  // students view their own schedule only
            header("Location: studentSchedule.php");
            exit();
        }
    } else {
        // login unsuccessful:
        $login_error = "err";
        // close the statement:
        $stmt->close();
        // close the connection:
        $conn->close();
    }
}
?>