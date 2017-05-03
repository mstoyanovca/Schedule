<?php
// validate name:
function validate_name($name) {
  if(preg_match("/^[A-Z][a-zA-Z -']+$/", $name)) {
    return true;
  } else {
    return false;
  }
}

// validate email:
function validate_email($email) {  
  $email = filter_var($email, FILTER_SANITIZE_EMAIL);  
  if(filter_var($email, FILTER_VALIDATE_EMAIL)) {  
    return true;  
  } else {  
    return false;  
  }
}

// validate phone number:
function validate_phone($phone_number) {
  if(preg_match("/^([1]-)?[0-9]{3}-[0-9]{3}-[0-9]{4}$/i", $phone_number)) {
      return true;
  } else {
      return false;
  }
}

// validate username:
function validate_user_name($username) {
    if(preg_match("/^[0-9a-zA-Z_.]{5,}$/", $username)) {
        return true;
    } else {
        return false;
    }
}

// validate password:
function validate_password($password) {
    if(preg_match("/^.*(?=.{8,})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$/", $password)) {
        return true;
    } else {
        return false;
    }
}

// random password generator:
function generatePassword($length) {
    $chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    $count = mb_strlen($chars);
    $result = '';
    for ($i = 0; $i<$length; $i++) {
        $index = rand(0, $count - 1);
        $result .= mb_substr($chars, $index, 1);
    }
    return $result;
}

// return day from index 1-6:
function getDay($day) {
    if($day=="1") {
        return "Monday";
    } elseif($day=="2") {
        return "Tuesday";
    } elseif($day=="3") {
        return "Wednesday";
    }  elseif($day=="4") {
        return "Thursday";
    }  elseif($day=="5") {
        return "Friday";
    }  elseif($day=="6") {
        return "Saturday";
    }
    return false;
}

// send registration e-mail:
function send_email($first_name, $last_name, $email, $username) {
    $to      = $email;
    $subject = "Registration";
    $message = 'Welcome '.$first_name.' '.$last_name.' to the Example Music School!'."\n\n";
    $message = $message . 'You have been registered successfully. You will be assigned a teacher and lesson day and time.'."\n";
    $message = $message . 'You can check your schedule online.'."\n";
    $message = $message . 'Your username is: ' . $username . '.' . "\n";
    $message = $message . 'If you have any questions, contact your teacher.';
    $headers = "From: Example School of Music <webmaster@exampleschoolofmusic.ca>";
    mail($to, $subject, $message, $headers);
}

// send new password e-mail:
function send_email2($first_name, $last_name, $email, $username, $password) {
    $to      = $email;
    $subject = "Change Password";
    $message = 'Hello '.$first_name.' '.$last_name.','."\n\n";
    $message = $message . 'Your username is: '.$username."\n";
    $message = $message . 'A new password has been generated: '.$password."\n";
    $message = $message . 'You can login now and change it.'."\n";
    $headers = "From: Example Music School <webmaster@exampleschoolofmusic.ca>";
    mail($to, $subject, $message, $headers);
}

// send changed password e-mail:
function send_email3($first_name, $last_name, $email) {
    $to      = $email;
    $subject = "Change Password";
    $message = 'Hello '.$first_name.' '.$last_name.','."\n\n";
    $message = $message . 'Your password has been changed successfully.'."\n";
    $headers = "From: Example Music School <webmaster@exampleschoolofmusic.ca>";
    mail($to, $subject, $message, $headers);
}
?>