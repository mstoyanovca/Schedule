// enables the update button:
var nameSelected = false;
var daySelected = false;
var startTimeSelected = false;
var endTimeSelected = false;
var day = 0;
function enableBtn1(str) {
    if (str=="") {
        nameSelected = false;
        document.getElementById("btn").disabled = true;
        return;
    } else {
        nameSelected = true;
        if(nameSelected&&daySelected&&startTimeSelected&&endTimeSelected) {
            document.getElementById("btn").disabled = false;
            return;
        }
    }
}
function enableBtn2(str) {
    if (str=="") {
        daySelected = false;
        document.getElementById("btn").disabled = true;
        // day selection determines start times:
        document.getElementById("time_from").disabled = true;
        return;
    } else {
        daySelected = true;
        // remember the selected day:
        day = str;
        // populate the time_from control:
        populateStartTime(str);
        if(nameSelected&&daySelected&&startTimeSelected&&endTimeSelected) {
            document.getElementById("btn").disabled = false;
            return;
        }
    }   
}
function enableBtn3(str) {
    if (str=="") {
        startTimeSelected = false;
        document.getElementById("btn").disabled = true;
        // end times are disabled, if no start time is selected:
        document.getElementById("time_to").disabled = true;
        return;
    } else {
        startTimeSelected = true;
        // populate the end times selection, when start time is selected:
        populateEndTime(str);
        if(nameSelected&&daySelected&&startTimeSelected&&endTimeSelected) {
            document.getElementById("btn").disabled = false;
            return;
        }
    }   
}
function enableBtn4(str) {
    if (str=="") {
        endTimeSelected = false;
        document.getElementById("btn").disabled = true;
        return;
    } else {
        endTimeSelected = true;
        if(nameSelected&&daySelected&&startTimeSelected&&endTimeSelected) {
            document.getElementById("btn").disabled = false;
            return;
        }
    }   
}
// populates the time_to select control:
function populateEndTime(str) {
    if(day!=6) {
        // lesson end times aray Mon-Fri:
        var week_times_to = ["16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45",
                             "19:00", "19:15", "19:30", "19:45", "20:00", "20:15", "20:30", "20:45", "21:00", "21:15",
                             "21:30"];
        var select = document.getElementById("time_to");
        select.options.length = 1; // clear out existing items
        for(var i=0; i<5; i++) {
            if(str<week_times_to.length) {
                select.options[select.options.length] = new Option(week_times_to[str], str);
                str++;
            }
        }
        document.getElementById("time_to").disabled = false;
        return;
    } else if(day==6) {
        // an array of lesson end times Saturday:
        var sat_times_to = ["9:30", "9:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45",
                            "12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00", "14:15",
                            "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30"];
        var select = document.getElementById("time_to");
        select.options.length = 1; // clear out existing items
        for(var i=0; i<5; i++) {
            if(str<sat_times_to.length) {
                select.options[select.options.length] = new Option(sat_times_to[str], str);
                str++;
            }
        }
        document.getElementById("time_to").disabled = false;
        return;
    }
}
// populates the time_from select control:
function populateStartTime(str) {
    if(str!=6) {
        // an array of lesson start times Mon-Fri:
        var week_times_from = ["16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00", "18:15",
                               "18:30", "18:45", "19:00", "19:15", "19:30", "19:45", "20:00", "20:15", "20:30", "20:45",
                               "21:00"];
        var select = document.getElementById("time_from");
        select.options.length = 1; // clear out existing items
        for(var i=0; i<week_times_from.length; i++) {
            select.options[select.options.length] = new Option(week_times_from[i], i);
        }
        // enable the time_from control:
        document.getElementById("time_from").disabled = false;
        return;
    } else if(str==6) {
        // an array of lesson start times Saturday:
        var sat_times_from = ["9:00", "9:15", "9:30", "9:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15",
                              "11:30", "11:45", "12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45",
                              "14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00"];
        var select = document.getElementById("time_from");
        select.options.length = 1; // clear out existing items
        for(var i=0; i<sat_times_from.length; i++) {
            select.options[select.options.length] = new Option(sat_times_from[i], i);
        }
        // enable the time_from control:
        document.getElementById("time_from").disabled = false;
        return;
    }
}