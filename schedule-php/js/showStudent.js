function showStudent(str) {
    // enables the button, if name is selected:
    if (str=="") {
        document.getElementById("btn").disabled = true;
        // no data is diplayed until a name is selected:
        document.getElementById("txtHint").innerHTML="";
        return;
    } else {
        document.getElementById("btn").disabled = false;
    }
    // creates XMLHttpRequest object:
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    xmlhttp.onreadystatechange=function() {
        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
            document.getElementById("txtHint").innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET","ajax/editStudentAjax.php?q="+str,true);
    xmlhttp.send();
}