<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<title>Account</title>
		
		<link rel="icon" href="resources/images/favicon.ico" type="image/x-icon" sizes="16x16">
		<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="resources/css/account.css" type="text/css" />
		
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
      		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    	<![endif]-->
	</head>
	<body ng-app="account" ng-init="teacherId='${profile.getTeacherId()}'">
		<div ng-controller="AccountController as ctrl" ng-cloak class="container-fluid ng-cloak">
		
			<header>
				<h4>
					<i>Teacher: {{ctrl.teacher.name}}</i>
				</h4>
			</header>

			<nav>
				<button type="button" id="schedule" title="Schedule" ng-click="ctrl.schedule()"></button>
				<button type="button" id="students" title="Students" ng-click="ctrl.students()"></button>
				<button type="button" id="update" title="Update" ng-click="ctrl.update()" ng-disabled="accountForm.$invalid"></button>
				<button type="button" id="delete" title="Delete" ng-click="ctrl.deleteTeacher()"></button>
				<button type="button" id="logout" title="Logout" ng-click="ctrl.logout()"></button>
			</nav>
			
			<form name="accountForm" class="form-horizontal" novalidate>
				<!-- Name -->
				<div class="row">
					<div class="form-group col-sm-12" ng-class="{'has-success has-feedback': accountForm.name.$valid, 
						'has-error': accountForm.name.$dirty && accountForm.name.$invalid}">
						<label for="name" class="col-sm-3 control-label">Name</label>
						<div class="col-sm-6">
							<input type="text" name="name" ng-model="ctrl.teacher.name" class="form-control"
								placeholder="Name" required ng-maxlength="50" title="Name">
						</div>
						<div class="col-sm-offset-3 col-sm-6">
							<div ng-show="accountForm.name.$dirty" class="error">
								<span ng-show="accountForm.name.$error.required">Name is required.</span> 
								<span ng-show="accountForm.name.$error.maxlength">Max length is 50 characters.</span>
							</div>
						</div>
					</div>
				</div>
				<!-- Email -->
				<div class="row">
					<div class="form-group col-sm-12" ng-class="{'has-success': accountForm.email.$valid, 
							'has-error': accountForm.email.$dirty && accountForm.email.$invalid}">
						<label for="email" class="col-sm-3 control-label">Email Address</label>
						<div class="col-sm-6">
							<input type="email" name="email" ng-model="ctrl.teacher.user.email" class="form-control" 
								placeholder="Email" required ng-maxlength="64" title="Email" change-email>
						</div>
						<div class="col-sm-offset-3 col-sm-6">
							<div ng-show="accountForm.email.$dirty" class="error">
								<span ng-show="accountForm.email.$error.required">Email is required.</span>
								<span ng-show="!accountForm.email.$error.required &&
											   !accountForm.email.$error.maxlength &&
											   !accountForm.email.$error.changeEmail && 
											   accountForm.email.$invalid">Email is invalid.</span>
								<span ng-show="accountForm.email.$error.maxlength">Max length is 64 characters.</span>
								<!-- Asynchronous validation: -->
								<span ng-show="accountForm.email.$pending.changeEmail">Checking if this email is available...</span>
		    					<span ng-show="accountForm.email.$error.changeEmail">This email is already registered.</span>
							</div>
						</div>
					</div>	
				</div>
			</form>
			
			<div class="row">
				<div class="col-sm-offset-3 col-sm-6">
					<button type="button" class="btn btn-link" ng-click="ctrl.changePassword()">
						Change Password
					</button>
				</div>
			</div>
			
			<!-- Scripts -->
			<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular.min.js"></script>
			<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular-animate.min.js"></script>
			<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular-resource.min.js"></script>
			<script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.1.2.js"></script>
			<script src="resources/js/account.js"></script>
			<script src="resources/js/controller/AccountController.js"></script>
			<script src="resources/js/service/teacher-service.js"></script>
			<script src="resources/js/directive/ChangeEmailDirective.js"></script>
			<script src="resources/js/directive/ValidPasswordDirective.js"></script>
			<script src="resources/js/directive/ConfirmPasswordDirective.js"></script>
		</div>
	</body>
</html>