<?php
// Initialize session:
session_start();

// delete session id cookie:
if(isset($PHPSESSID)) {
    session_destroy(  );
}

// Delete all session variables:
session_destroy();

// Redirect to the home page:
header('Location: ../index.php');
?>