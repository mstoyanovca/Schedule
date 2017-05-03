<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<title>Schedule</title>
		
		<link rel="icon" href="resources/images/favicon.ico" type="image/x-icon" sizes="16x16">
		<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="resources/css/schedule.css" type="text/css" />
		
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>

	<body ng-app="schedule">
		<div ng-controller="ScheduleController as ctrl" ng-cloak class="container-fluid ng-cloak">
			<header>
				<h4>
					<i>Teacher: <c:out value="${profile.getTeacherName()}" /></i>
				</h4>
			</header>
	
			<nav>
				<button id="add" type="button" title="Add а lesson" ng-click="ctrl.create()"></button>
				<button id="students" type="button" title="Students" ng-click="ctrl.students()"></button>
				<!-- <select id="settings" title="Settings" ng-change="ctrl.settings()" ng-model="options" class="settings">
					<option></option>
					<option value="account">Account</option>
					<option value="logout">Logout</option>
				</select> -->
				<select id="settings" title="Settings" ng-change="ctrl.settings(option)" 
					ng-model="option" ng-options="option.name for option in ctrl.options" 
					class="settings">
				</select>
			</nav>
			
			<!-- Day of week tabs -->
			<uib-tabset class="tabset">
				<uib-tab ng-repeat="tab in ctrl.tabs" select="ctrl.selectDOW(tab.title)" class="tab">
					<uib-tab-heading>
						<h4>{{tab.title}}</h4>
					</uib-tab-heading>
      				<!-- Tab content -->
                  	<table class="table table-hover">
                    	<thead>
                        	<tr class="row">
                            	<th class="col-sm-1 col-xs-2">Start</th>
                              	<th class="col-sm-1 col-xs-2">End</th>
                              	<th class="col-sm-4 col-xs-3">Name</th>
                              	<th class="col-sm-4 col-xs-3">Notes</th>
                              	<th class="col-sm-2 col-xs-2"></th>
                          	</tr>
                      	</thead>
                      	<tbody>
                        	<tr ng-repeat="lesson in ctrl.lessons | orderBy: ['startTime', 'endTime']" 
                        		ng-if = "tab.title.toLowerCase() == lesson.dow.toLowerCase()" class="row">
	                            <td>{{lesson.startTime | date: "HH:mm"}}</td>
	                            <td>{{lesson.endTime | date: "HH:mm"}}</td>
	                            <td ng-bind="lesson.student.name"></td>
	                            <td ng-bind="lesson.note"></td>
	                            <td>
	                            	<button type="button" ng-click="ctrl.edit(lesson)" class="btn btn-info btn-sm">
	                            		<span class="glyphicon glyphicon-pencil"></span>
	                            	</button>
	                              	<button type="button" ng-click="ctrl.remove(lesson)" class="btn btn-danger btn-sm">
	                              		<span class="glyphicon glyphicon-remove"></span>
	                              	</button>
	                            </td>
                          	</tr>
                      	</tbody>
                  	</table>

    			</uib-tab>
  			</uib-tabset>
			<!-- Add/edit lesson modal -->
			<script type="text/ng-template" id="lesson.html">
        		<!-- Content in addLesson.html partial -->
    		</script>
		</div>
		
		<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular.js"></script>
		<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular-animate.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular-resource.min.js"></script>
		<script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.1.2.js"></script>
		<script src="resources/js/schedule.js"></script>
		<script src="resources/js/controller/ScheduleController.js"></script>
		<script src="resources/js/controller/LessonModalCtrl.js"></script>
		<script src="resources/js/service/student-service.js"></script>
		<script src="resources/js/service/lesson-service.js"></script>
	</body>
</html>
