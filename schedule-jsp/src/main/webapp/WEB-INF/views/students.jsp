<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<title>Students</title>
		
		<link rel="icon" href="resources/images/favicon.ico" type="image/x-icon" sizes="16x16">
		<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="resources/css/students.css" type="text/css" />
		
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    	<!--[if lt IE 9]>
      		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    	<![endif]-->
	</head>
	
	<body ng-app="students">
		<div ng-controller="StudentController as ctrl" ng-cloak class="container-fluid ng-cloak">
		
			<header>
				<h4>
					<i>Teacher: <c:out value="${profile.getTeacherName()}" /></i>
				</h4>
			</header>

			<nav>
				<button type="button" id="add" title="Add а student" ng-click="ctrl.create()"></button>
				<button type="button" id="schedule" title="Schedule" ng-click="ctrl.schedule()"></button>
				<select id="settings" title="Settings" ng-change="ctrl.settings(option)" 
					ng-model="option" ng-options="option.name for option in ctrl.options" 
					class="settings">
				</select>
			</nav>

			<!-- Students list accordion -->
			<div class="row">
				<div class="col-sm-8">
					<script type="text/ng-template" id="group-template.html">
    					<div class="panel {{panelClass || 'panel-default'}}">
      						<div class="panel-heading">
        						<h4 class="panel-title" style="color:#fa39c3">
          							<a href tabindex="0" class="accordion-toggle" ng-click="toggleOpen()" uib-accordion-transclude="heading">
										<span ng-class="{'text-muted': isDisabled}">{{heading}}</span>
									</a>
        						</h4>
      						</div>
      						<div class="panel-collapse collapse" uib-collapse="!isOpen">
        						<div class="panel-body" style="text-align: right" ng-transclude></div>
      						</div>
    					</div>
  					</script>
					<uib-accordion> 
						<uib-accordion-group heading="{{student.name}}" ng-repeat="student in ctrl.students | orderBy:'name'">
							<ul>
								<li ng-repeat="phone in student.phones"><p class="note">{{phone.number + " " + phone.type}}</p></li>
							</ul>
							<p class="note">{{student.note}}</p>
							<br>
							<button class="btn btn-info btn-sm" ng-click="ctrl.edit(student)" title="Edit">
								<span class="glyphicon glyphicon-pencil"></span>
							</button>
							<button class="btn btn-danger btn-sm" ng-click="ctrl.remove(student)" title="Delete">
								<span class="glyphicon glyphicon-remove"></span>
							</button>
						</uib-accordion-group> 
					</uib-accordion>
				</div>
			</div>
			
			<!-- Add/edit student modal -->
			<script type="text/ng-template" id="student.html">
        		<!-- Content in student.html partial -->
    		</script>

			<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular.min.js"></script>
			<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular-animate.min.js"></script>
			<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular-resource.min.js"></script>
			<script src="<c:url value="/resources/js/students.js" />" type="text/javascript"></script>
			<script src="<c:url value="/resources/js/service/student-service.js" />" type="text/javascript"></script>
			<script src="<c:url value="/resources/js/controller/StudentController.js" />" type="text/javascript"></script>
			<script src="<c:url value="/resources/js/controller/StudentModalCtrl.js" />" type="text/javascript"></script>
			<script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.1.2.js"></script>
		</div>
	</body>
</html>
