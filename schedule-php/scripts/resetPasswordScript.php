<?php
// validation functions:
include 'include/functions.php';
// error flag:
$err = "";

// sanitize, validate, preserve input for resubmission:
if(empty($_POST['first_name'])) {
    $err_fn = "Please enter first name";
    $err = "err";
} else {
    if(!validate_name($_POST['first_name'])) {
        $err_fn = "First name is invalid";
        $err = "err";
    } else {
        $first_name = $_POST['first_name'];
    }
}
if(empty($_POST['last_name'])) {
    $err_ln = "Please enter last name";
    $err = "err";
} else {
    if(!validate_name($_POST['last_name'])) {
        $err_ln = "Last name is invalid";
        $err = "err";
    } else {
        $last_name = $_POST['last_name'];
    }
}
if(empty($_POST['email'])) {
    $err_em = "Please enter e-mail";
    $err = "err";
} else {
    if(!validate_email($_POST['email'])) {
        $err_em = "E-mail is invalid";
        $err = "err";
    } else {
        $email = $_POST['email'];
    }
}
if($_POST['question']=="") {
    $err_qu = "Please select password recovery question";
    $err = "err";
} else {
    $question = $_POST['question'];
}
if(empty($_POST['answer'])) {
    $err_an = "Please enter your answer";
    $err = "err";
} else {
    $answer = filter_var($_POST['answer'], FILTER_SANITIZE_STRING);
    if(empty($answer)) {
        $err_an = "Invalid answer";
    }
}

// if there is no missing or invalid input, check if this record exists: 
if($err == "") {
    include 'classes/User.php';
    $user = new User($first_name, $last_name, $email, "", "", "", $question, $answer);
    if(!$user->reset_pw()) {
        $err_un="We have no record for the data that you have entered";
    }
}
?>