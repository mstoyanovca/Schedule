<?php
// initialize variables:
// input fields storage variables:
$first_name="";          // first name
$last_name="";           // last name
$email="";               // e-mail
$home_phone="";          // home phone
$cell_phone="";          // cell phone
$work_phone="";          // wokr phone
$notes="";               // notes
$username="";            // username
$question="";            // question
$answer="";              // answer
// missing entry flags:
$err_fn="";
$err_ln="";
$err_em="";
$err_ph="";              // phone
$err_un="";
$err_pw="";              // password
$err_pwcn="";            // password confirmation
$err_qu="";              // question
$err_an="";              // answer
// values already taken:
$existing_un="";         // username already taken
// passwords not matching:
$matching_pw="";
// call the script file, when the form is submitted:
if(isset($_POST["submit"])) {
    include 'scripts/registerScript.php';
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="shortcut icon" href="images/piano.jpg" type="image/x-icon"/>
<link rel="stylesheet" href="css/entry.css"/>
<title>Register</title>
</head>

<body>
<form action="" method="POST">
    <h2>Register:</h2>
<p>(* mandatory fields)</p>

<div class="container">
    <div class="label">
        First Name:
    </div>
    <div class="left">
        <input type="text" name="first_name" value= '<?php echo $first_name; ?>'/>*
        <?php if(!empty($err_fn)) {echo "<span style=color:red>".$err_fn."</span>"; } ?><br/>
    </div>
    <div class="clear"></div>
    
    <div class="label">
        Last Name:
    </div>
    <div class="left">
        <input type="text" name="last_name" value= '<?php echo $last_name; ?>'/>*
        <?php if(!empty($err_ln)) {echo "<span style=color:red>".$err_ln."</span>";} ?><br/>
    </div>
    <div class="clear"></div>

    <div class="label">
        E-mail address:
    </div>
    <div class="left">
        <input type="text" name="email" value= '<?php echo $email; ?>'/>*
        <?php if(!empty($err_em)) {echo "<span style=color:red>".$err_em."</span>";} ?><br/>
    </div>
    <div class="clear"></div>

    <p>Please enter at least one phone number: *</p>
    <?php if(!empty($err_ph)) {echo "<span style=color:red>".$err_ph."</span><br/>";} ?>
    
    <div class="label">
        Home phone:
    </div>
    <div class="left">
        <input type="text" name="home_phone" value= '<?php echo $home_phone; ?>'/>
        <?php if(!empty($err_hp)) {echo "<span style=color:red>".$err_hp."</span>";} ?>
    </div>
    <div class="clear"></div>
    
    <div class="label">
        Cell Phone:
    </div>
    <div class="left">
        <input type="text" name="cell_phone" value= '<?php echo $cell_phone; ?>'/>
        <?php if(!empty($err_cp)) {echo "<span style=color:red>".$err_cp."</span>";} ?>
    </div>
    <div class="clear"></div>
 
    <div class="label">
        Work Phone:
    </div>
    <div class="left">
        <input type="text" name="work_phone" value= '<?php echo $work_phone; ?>'/>
        <?php if(!empty($err_wp)) {echo "<span style=color:red>".$err_wp."</span>";} ?>
    </div>
    <div class="clear"></div>
    
    <p>Please enter your lesson day and time preferences:</p>
    <input name="notes" type="text" style="width:300px" value="<?php echo $notes; ?>"></input><br/><br/>
    
    <div class="label">
        E-mail address:
    </div>
    <div class="left">
        <input type="text" name="email" value= '<?php echo $email; ?>'/>*
        <?php if(!empty($err_em)) {echo "<span style=color:red>".$err_em."</span>";} ?><br/>
    </div>
    <div class="clear"></div>

    <div class="label">
        Select user name:
    </div>
    <div class="left">
        <input type="text" name="username" value= '<?php echo $username; ?>'/>*
        <?php if(!empty($err_un)) {echo "<span style=color:red>".$err_un."</span>";} ?>
        <?php if(!empty($existing_un)) {echo "<span style=color:red>".$existing_un."</span>";} ?><br/>
    </div>
    <div class="clear"></div>

    <div class="label">
        Select password:
    </div>
    <div class="left">
        <input type="password" name="password1"/>*
        <?php if(!empty($err_pw)) {echo "<span style=color:red>".$err_pw."</span>";} ?><br/>
    </div>
    <div class="clear"></div>
 
    <div class="label">
        Confirm password:
    </div>
    <div class="left">
        <input type="password" name="password2"/>*
        <?php if(!empty($err_pwcn)) {echo "<span style=color:red>".$err_pwcn."</span>";} ?><br/>
        <?php if(!empty($matching_pw)) {echo "<span style=color:red>$matching_pw</span><br/>";} ?><br/>
    </div>
    <div class="clear"></div>

    <select style="width:305px" name="question">
        <option value="">Select a password recovery question:</option>
        <option value="1" <?php if($question=="1") echo 'selected="selected";'?>>What is your mother's maiden name?</option>
        <option value="2" <?php if($question=="2") echo 'selected="selected";'?>>What is your pet's name?</option>
        <option value="3" <?php if($question=="3") echo 'selected="selected";'?>>What make was your first car?</option>
        <option value="4" <?php if($question=="4") echo 'selected="selected";'?>>What is the name of your best friend?</option>
        <option value="5" <?php if($question=="5") echo 'selected="selected";'?>>What is the name of your eldest child?</option>
    </select>*
    <?php if(!empty($err_qu)) {echo "<span style=color:red>".$err_qu."</span>";} ?><br/>

    <div class="label">
        Answer:
    </div>
    <div class="left">
        <input type="text" name="answer" value= '<?php echo $answer; ?>'/>*
        <?php if(!empty($err_an)) {echo "<span style=color:red>".$err_an."</span>";} ?>
    </div>
    <div class="clear"></div>
</div>

<p><input name="submit" type="submit" value="Register"/> 
   <a href="index.php">Cancel</a></p>
</form>
</body>
</html>