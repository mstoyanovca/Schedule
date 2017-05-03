<?php
class Entry {
    private $time_from;
    private $time_to;
    private $day;
    private $student_id;
    
    function __construct($time_from, $time_to, $day, $student_id) {
        $this->time_from = $time_from;
        $this->time_to = $time_to;
        $this->day = $day;
        $this->student_id = $student_id;
    }
    
    function add() {
        // functions:
        include 'include/functions.php';
        // connect to the database:
        require_once 'include/connect.php';
        // check if the selected times are available:
        // no lesson starts for the span of the new lesson:
        for($i=$this->time_from; $i<$this->time_to+2; $i++) {
            $command = "SELECT * FROM ".getDay($this->day)." WHERE time_from=".$i.";";
            $result = $conn->query($command);
            while($data=mysqli_fetch_object($result)) {
                $_SESSION['err'] = "err";
                header("Location: schedule.php");
                exit();
            }
        }
        // no lesson ends in the span of the new one:
        for($i=0; $i<$this->time_from; $i++) {
            $command = "SELECT * FROM ".getDay($this->day)." WHERE time_from=".$i.";";
            $result = $conn->query($command);
            while($data=mysqli_fetch_object($result)) {
                if(($data->time_from+$data->units-1)>=$this->time_from) {
                    $_SESSION['err'] = "err";
                    header("Location: schedule.php");
                    exit();
                }
            }
        }
        // length of the lesson in units of 15 minutes:
        $units = $this->time_to - $this->time_from + 2;  // indexes
        // get the properties of the selected student:
        $command = "SELECT * FROM students WHERE id='$this->student_id';";
        $result = $conn->query($command);
        if($data = mysqli_fetch_object($result)) {
            $first_name=$data->first_name;
            $last_name=$data->last_name;
            $home_phone=$data->home_phone;
            $cell_phone=$data->cell_phone;
            $work_phone=$data->work_phone;
        }
        // insert new record:
        $command = "INSERT INTO ".getDay($this->day)." (time_from, units, first_name, last_name, home_phone, cell_phone,
                    work_phone) VALUES ('$this->time_from', '$units', '$first_name', '$last_name', '$home_phone', 
                   '$cell_phone', '$work_phone');";
        $conn->query($command);
        // close connection:
        $conn->close();
        // return to schedule:
        header('Location: schedule.php');
    }
    
    function remove() {
        // arrays of possible lesson start/end times:
        require_once 'include/arrays.php';
        // functions:
        require_once 'include/functions.php';
        if(isset($_POST["submit"])) {
            // connect to the database:
            require_once 'include/connect.php';
            // delete the lesson in the selected day at the selected time:
            $command = "DELETE FROM ".getDay($_POST['day'])." WHERE time_from=".$_POST['time_from'].";";
            $conn->query($command);
            // close the connection:
            $conn->close();
            // return to schedule:
            header("Location: schedule.php");
        }
    }
}
?>
