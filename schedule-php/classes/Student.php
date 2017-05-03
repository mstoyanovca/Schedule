<?php
class Student {
    private $first_name;
    private $last_name;
    private $home_phone;
    private $cell_phone;
    private $work_phone;
    
    function __construct($first_name, $last_name, $home_phone, $cell_phone, $work_phone) {
        $this->first_name = $first_name;
        $this->last_name = $last_name;
        $this->home_phone = $home_phone;
        $this->cell_phone = $cell_phone;
        $this->work_phone = $work_phone;
    }
    
    function add() {
        // connect to the database:
        require_once 'include/connect.php';
        // execute query:
        $command = "INSERT INTO students (first_name, last_name, home_phone, cell_phone, work_phone)
                    VALUES ('$this->first_name', '$this->last_name', '$this->home_phone', '$this->cell_phone',
                            '$this->work_phone');";
        $conn->query($command);
        // close the connection:
        $conn->close();
    }
    
    function edit($student_id) {
        require_once 'include/connect.php';
        // edit student's data:
        $command = "UPDATE students SET first_name='$this->first_name', last_name='$this->last_name', 
                    home_phone='$this->home_phone', cell_phone='$this->cell_phone', work_phone='$this->work_phone'
                    WHERE id='$student_id';";
        $conn->query($command);
        // close the connection:
        $conn->close();
        // return to the student's page:
        header("Location: students.php");
    }
    
    function remove($student_id) {
        // connect to the database:
        require_once 'include/connect.php';
        // execute query:
        $command = "DELETE FROM students WHERE id='$_POST[student]';";
        $conn->query($command);
        // close the connection:
        $conn->close();
        // return to the students' table:
        header("Location: students.php");
    }
    
    function displaySchedule() {
        // connect to the database:
        require_once 'include/connect.php';
        require 'include/functions.php';
        require 'include/arrays.php';
        for($i=1; $i<7; $i++) {                  // days
            $command = "SELECT * FROM ".getDay($i)." WHERE first_name='$this->first_name' AND last_name='$this->last_name';";
            // execute query:
            $result = $conn->query($command);
            // print result:
            while($data = mysqli_fetch_object($result)) {
                if($i!=6) {
                    print "<p>Day: ".getDay($i)." From: ".$week_times_from[$data->time_from]." To: "
                          .$week_times_to[$data->time_from+$data->units-2]."</p>";
                } elseif($i==6) {
                    print "<p>Day: ".getDay($i)." From: ".$sat_times_from[$data->time_from]." To: "
                          .$sat_times_to[$data->time_from+$data->units-2]."</p>";
                }
            }
        }
        // close the connection:
        $conn->close();
    }
}
?>
