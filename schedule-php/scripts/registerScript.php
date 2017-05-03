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
if(empty($_POST['home_phone'])&&empty($_POST['cell_phone'])&&empty($_POST['work_phone'])) {
    $err_ph = "Please enter at least one phone number";
    $err = "err";
}
if(!empty($_POST['home_phone'])) {
    if(!validate_phone($_POST['home_phone'])) {
        $err_hp = "Home phone is invalid; (1-)222-333-4444 format please";
        $err = "err";
    } else {
        $home_phone = $_POST['home_phone'];
    }
}
if(!empty($_POST['cell_phone'])) {
    if(!validate_phone($_POST['cell_phone'])) {
        $err_cp = "Cell phone is invalid; (1-)222-333-4444 format please";
        $err = "err";
    } else {
        $cell_phone = $_POST['cell_phone'];
    }
}
if(!empty($_POST['work_phone'])) {
    if(!validate_phone($_POST['work_phone'])) {
        $err_wp = "Work phone is invalid; (1-)222-333-4444 format please";
        $err = "err";
    } else {
        $work_phone = $_POST['work_phone'];
    }
}
$_POST['notes'] = filter_var($_POST['notes'], FILTER_SANITIZE_STRING);
$notes = $_POST['notes'];  // it could be empty, if you wish so, but not bugged
if(empty($_POST['username'])) {
    $err_un = "Please select your username";
    $err = "err";
} else {
    if(!validate_user_name($_POST['username'])) {
        $err_un = "Invalid username; letters, numbers, \"_\" and \".\", min lentgh 5";
        $err = "err";
    } else {
        $username = $_POST['username'];
    }
}
if(empty($_POST['password1'])) {
    $err_pw = "Please select a password";
    $err = "err";
} else {
    if(!validate_password($_POST['password1'])) {
        $err_pw = "Invalid password: number, small, capital letter, min length 8";
        $err = "err";
    }
}
if(empty($_POST['password2'])) {
    $err_pwcn = "Please confirm your password";
    $err = "err";
}
// check for matching passwords:
if($_POST['password1']!=$_POST['password2']) {
    $matching_pw = "Your passwords don't match";
    $err = "err";
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

// check for duplicate username:
require_once 'classes/User.php';
$user = new User($first_name, $last_name, $email, $username, $_POST['password1'], "student", $question, $answer);
if($user->duplicate()) {
    $existing_un="This username is already taken";
    $err = "err";
}

// if there is no missing or invalid input, or duplicate username, register the 
// new student, and send confirmation e-mail: 
if($err == "") {
    $user->register();
}
?>