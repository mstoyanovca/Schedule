<?php
class User {
    private $first_name;
    private $last_name;
    private $email;
    private $username;
    private $password;
    private $privilege;
    private $question;
    private $answer;
    
    function __construct($first_name, $last_name, $email, $username, $password, $privilege, $question, $answer) {
        $this->first_name = $first_name;
        $this->last_name = $last_name;
        $this->email = $email;
        $this->username = $username;
        $this->password = $password;
        $this->privilege = $privilege;
        $this->question = $question;
        $this->answer = $answer;
    }
    
    // register the new student into the database:
    function register() {
        // connect to the database:
        require 'include/connect.php';
        // use prepared statements against SQL injection:
        $stmt = $conn->prepare("INSERT INTO users (first_name, last_name, email, username, password, privilege, question,
                                answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
        // bind the input of the prepared statement:
        $this->password = md5($this->password);
        $stmt->bind_param("ssssssss", $this->first_name, $this->last_name, $this->email, $this->username, $this->password,
                           $this->privilege, $this->question, $this->answer);
        // execute the prepared statement:
        $stmt->execute();
        // close the statement:
        $stmt->close();
        // close the connection:
        $conn->close();
        // sent confirmation e-mail:
        send_email($this->first_name, $this->last_name, $this->email, $this->username);
        // return to the home page to login:
        header("Location: index.php");
    }
    
    // check if the selected username at registration is not taken:
    function duplicate() {
        // connect to the database:
        include 'include/connect.php';
        // use prepared statements against SQL injection:
        $stmt = $conn->prepare("SELECT username FROM users WHERE username=? LIMIT 1;");
        // bind the input of the prepared statement:
        $stmt->bind_param("s", $this->username);
        // bind the output of the prepared statement:
        $stmt->bind_result($col1);
        // execute the prepared statement:
        $stmt->execute();
        // Check for duplicate username:
        while($stmt->fetch()) {
            // username is already taken:
            if($this->username==$col1) {
                return true;
            }
        }
        // close the statement:
        $stmt->close();
        // close the connection:
        $conn->close();
    }
    
    // update password in the database:
    function change_pw($user_id) {    
        // connect to the database:
        require_once 'include/connect.php';
        // use prepared SQL statements against SQL injection:
        $stmt = $conn->prepare("UPDATE users SET password=? WHERE user_id='$user_id';");
        // "hash" the password:
        $this->password = md5($this->password);
        // bind the inputs of the prepared statement:
        $stmt->bind_param("s", $this->password);
        // execute the prepared statement:
        $stmt->execute();
        // close the statement:
        $stmt->close();
        // find the e-mail address of the logged in person:
        $command = "SELECT email FROM users WHERE user_id='$user_id';";
        $result = $conn->query($command);
        // get email:
        $email = "";
        if($data = mysqli_fetch_object($result)) {
            $email = $data->email;
        }
        // close the connection:
        $conn->close();
        // send warning email:
        send_email3($this->first_name, $this->last_name, $email);
        // return to the home page:
        header("Location: index.php");
    }
    
    function reset_pw() {
        // connect to the database:
        include 'include/connect.php';
        // use prepared statements against SQL injection:
        // check records for this name, e-mail, pw recovery question and answer:
        $stmt = $conn->prepare("SELECT username FROM users WHERE first_name=? AND last_name=? AND email=? AND question=? AND
                                answer=? LIMIT 1;");
        // bind the input of the prepared statement:
        $stmt->bind_param("sssss", $this->first_name, $this->last_name, $this->email, $this->question, $this->answer);
        // bind the output of the prepared statement:
        $stmt->bind_result($col1);
        // execute the prepared statement:
        $stmt->execute();
        // Check for a match:
        if($stmt->fetch()) {
            // get username:
            $this->username = $col1;
            // close the statement:
            $stmt->close();
            // generate new password:
            $this->password = generatePassword(5);
            // sent confirmation e-mail:
            send_email2($this->first_name, $this->last_name, $this->email, $this->username, $this->password);
            // change password in the database:
            $this->password = md5($this->password);
            $command = "UPDATE users SET password='$this->password' WHERE username='$this->username';";
            $conn->query($command);
            // close the connection:
            $conn->close();
            // return to the home page to login:
            header("Location: index.php");
            return true;
        } else {
            return false;
        }
    }
}

?>
