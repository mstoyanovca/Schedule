<?php
// script to execute, when the form is submitted:
if(isset($_POST["submit"])) {
    // conect to the MySQL server:
    $con = mysql_connect("localhost","root","");
    if (!$con) {
        die('Could not connect: ' . mysql_error());
    }
    // create database:
    if (mysql_query("CREATE DATABASE schedule", $con)) {
        echo "Database created";
    } else {
        echo "Error creating database: " . mysql_error();
    }
    // select database:
    mysql_select_db("schedule", $con);
    // create table users:
    $sql = "CREATE TABLE users (user_id int NOT NULL AUTO_INCREMENT, first_name varchar(15), last_name varchar(15),
            email varchar(100), username varchar(15), password varchar(40), privilege varchar(15), question varchar(100),
            answer varchar(100), PRIMARY KEY (user_id));";
    if(mysql_query($sql,$con)) {
        echo "Table users created";
    } else {
        echo "Error creating table users: " . mysql_error();
    }
    // create table students:
    $sql = "CREATE TABLE students (id int NOT NULL AUTO_INCREMENT, first_name varchar(15), last_name varchar(15),
            home_phone varchar(12), cell_phone varchar(12), work_phone varchar(12), PRIMARY KEY (id));";
    if(mysql_query($sql,$con)) {
        echo "Table students created";
    } else {
        echo "Error creating table students: " . mysql_error();
    }
    // create table monday:
    $sql = "CREATE TABLE monday (id int NOT NULL AUTO_INCREMENT, time_from int(2), units int(1), first_name varchar(15),
            last_name varchar(15), home_phone varchar(12), cell_phone varchar(12), work_phone varchar(12), PRIMARY KEY (id));";
    if(mysql_query($sql,$con)) {
        echo "Table monday created";
    } else {
        echo "Error creating table monday: " . mysql_error();
    }
    // create table tuesday:
    $sql = "CREATE TABLE tuesday (id int NOT NULL AUTO_INCREMENT, time_from int(2), units int(1), first_name varchar(15),
            last_name varchar(15), home_phone varchar(12), cell_phone varchar(12), work_phone varchar(12), PRIMARY KEY (id));";
    if(mysql_query($sql,$con)) {
        echo "Table tuesday created";
    } else {
        echo "Error creating table tuesday: " . mysql_error();
    }
    // create table wednesday:
    $sql = "CREATE TABLE wednesday (id int NOT NULL AUTO_INCREMENT, time_from int(2), units int(1), first_name varchar(15),
            last_name varchar(15), home_phone varchar(12), cell_phone varchar(12), work_phone varchar(12), PRIMARY KEY (id));";
    if(mysql_query($sql,$con)) {
        echo "Table wednesday created";
    } else {
        echo "Error creating table wednesday: " . mysql_error();
    }
    // create table thursday:
    $sql = "CREATE TABLE thursday (id int NOT NULL AUTO_INCREMENT, time_from int(2), units int(1), first_name varchar(15),
            last_name varchar(15), home_phone varchar(12), cell_phone varchar(12), work_phone varchar(12), PRIMARY KEY (id));";
    if(mysql_query($sql,$con)) {
        echo "Table thursday created";
    } else {
        echo "Error creating table thursday: " . mysql_error();
    }
    // create table friday:
    $sql = "CREATE TABLE friday (id int NOT NULL AUTO_INCREMENT, time_from int(2), units int(1), first_name varchar(15),
            last_name varchar(15), home_phone varchar(12), cell_phone varchar(12), work_phone varchar(12), PRIMARY KEY (id));";
    if(mysql_query($sql,$con)) {
        echo "Table friday created";
    } else {
        echo "Error creating table friday: " . mysql_error();
    }
    // create table saturday:
    $sql = "CREATE TABLE saturday (id int NOT NULL AUTO_INCREMENT, time_from int(2), units int(1), first_name varchar(15),
            last_name varchar(15), home_phone varchar(12), cell_phone varchar(12), work_phone varchar(12), PRIMARY KEY (id));";
    if(mysql_query($sql,$con)) {
        echo "Table saturday created";
    } else {
        echo "Error creating table saturday: " . mysql_error();
    }
    // insert a user with teacher's privileges for a demo:
    $sql = "INSERT INTO users (first_name, last_name, username, password, privilege) VALUES ('Firstname', 'Lastname', 'username', 
            md5('password'), 'teacher')";
    if(mysql_query($sql,$con)) {
        echo "User added";
    } else {
        echo "Error adding user: " . mysql_error();
    }
    mysql_close($con);
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html" charset=utf-8"/>
<title>Install tables</title>
</head>

<body>
<h2>Welcome to the Example Music School Installation Page</h2>
<form method="post" action="">
<p>Click "Install" to populate the tables:</p>
<p><input type='submit' name='submit' value='Install'/></p>
</form>
</body>
</html>
