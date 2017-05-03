<?php
// initialize variables:
// input fields storage variables:
$first_name="";          // first name
$last_name="";           // last name
$email="";               // e-mail
$username="";            // username
$question="";            // question
$answer="";              // answer
// missing entry flags:
$err_fn="";
$err_ln="";
$err_em="";
$err_un="";
$err_qu="";              // question
$err_an="";              // answer
// call the script file, when the form is submitted:
if(isset($_POST["submit"])) {
    include 'scripts/resetPasswordScript.php';
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="shortcut icon" href="images/piano.jpg" type="image/x-icon"/>
<link rel="stylesheet" href="css/entry.css"/>
<title>Reset password</title>
</head>

<body>
<form action="" method="POST">
<h2>Please enter:</h2>

<div class="container">
    <div class="label">
        First Name:
    </div>
    <div class="left">
        <input type="text" name="first_name" value= '<?php echo $first_name; ?>'/>
        <?php if(!empty($err_fn)) {echo "<span style=color:red>".$err_fn."</span>"; } ?>
    </div>
    <div class="clear"></div>
    <div class="clear"></div>
 
    <div class="label">
        Last Name:
    </div>
    <div class="left">
        <input type="text" name="last_name" value= '<?php echo $last_name; ?>'/>
        <?php if(!empty($err_ln)) {echo "<span style=color:red>".$err_ln."</span>";} ?>
    </div>
    <div class="clear"></div>

    <div class="label">
        E-mail address:
    </div>
    <div class="left">
        <input type="text" name="email" value= '<?php echo $email; ?>'/>
        <?php if(!empty($err_em)) {echo "<span style=color:red>".$err_em."</span>";} ?>
    </div>
    <div class="clear"></div>

    <select style="width:305px" name="question">
        <option value="">Select a password recovery question:</option>
        <option value="1" <?php if($question=="1") echo 'selected="selected";'?>>What is your mother's maiden name?</option>
        <option value="2" <?php if($question=="2") echo 'selected="selected";'?>>What is your pet's name?</option>
        <option value="3" <?php if($question=="3") echo 'selected="selected";'?>>What make was your first car?</option>
        <option value="4" <?php if($question=="4") echo 'selected="selected";'?>>What is the name of your best friend?</option>
        <option value="5" <?php if($question=="5") echo 'selected="selected";'?>>What is the name of your eldest child?</option>
    </select>
    <?php if(!empty($err_qu)) {echo "<span style=color:red>".$err_qu."</span>";} ?><br/>
    
    <div class="label">
        Answer:
    </div>
    <div class="left">
        <input type="text" name="answer" value= '<?php echo $answer; ?>'/>
        <?php if(!empty($err_an)) {echo "<span style=color:red>".$err_an."</span>";} ?>
        <?php if(!empty($err_un)) {echo "<span style=color:red>".$err_un."</span>";} ?>
    </div>
    <div class="clear"></div>
</div>

<p><input name="submit" type="submit" value="Submit"/>
   <a href="index.php">Cancel</a></p>
<p>Check your e-mail for login instructions.</p>
</body>
</form>
</html>