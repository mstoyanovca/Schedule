<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<title>Change Password</title>
		
		<link rel="icon" href="resources/images/favicon.ico" type="image/x-icon" sizes="16x16">
		<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="resources/css/change-password.css" type="text/css" />
		
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
      		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    	<![endif]-->
	</head>
	<body ng-app="change-password" ng-init="teacherId='${profile.getTeacherId()}'">
		<div ng-controller="ChangePasswordController as ctrl" ng-cloak class="container-fluid ng-cloak">
		
			<header>
				<h4>
					<i>Teacher: {{ctrl.teacher.name}}</i>
				</h4>
			</header>

			<nav>
				<button type="button" id="change-password" title="Change Password" 
					ng-click="ctrl.changePassword()" ng-disabled="changePasswordForm.$invalid">
				</button>
			</nav>
			
			<form name="changePasswordForm" class="form-horizontal" novalidate>
				<!-- Password -->
				<div class="row">
					<div class="form-group col-sm-12" ng-class="{'has-success': changePasswordForm.password.$dirty &&
							changePasswordForm.password.$valid, 'has-error': changePasswordForm.password.$dirty && 
							changePasswordForm.password.$invalid}">
						<label for="password" class="col-sm-3 control-label">Password</label>
						<div class="col-sm-6">
							<input type="password" name="password" ng-model="ctrl.teacher.user.password"
								class="form-control" placeholder="6 - 20 characters" ng-minlength="6" ng-maxlength="20"
								required title="Password" valid-password>
						</div>
						<div class="col-sm-offset-3 col-sm-6">
							<div ng-show="changePasswordForm.password.$dirty" class="error">
								<span ng-show="changePasswordForm.password.$error.required">Password is required.</span>
								<span ng-show="changePasswordForm.password.$error.minlength">Min length is 6 characters.</span>
								<span ng-show="changePasswordForm.password.$error.maxlength">Max length is 20 characters.</span>
								<span ng-show="!changePasswordForm.password.$error.required &&
											   !changePasswordForm.password.$error.minlength &&
											   !changePasswordForm.password.$error.maxlength &&
											   changePasswordForm.password.$error.validPassword">Password must contain 
									an upper case and a lower case letter, a number, and a special character.</span>
							</div>
						</div>
					</div>
				</div>
				<!-- Confirm password -->
				<div class="row">
					<div class="form-group col-sm-12" ng-class="{'has-success': changePasswordForm.confirmPassword.$dirty &&
							changePasswordForm.confirmPassword.$valid, 'has-error': changePasswordForm.confirmPassword.$dirty && 
							changePasswordForm.confirmPassword.$invalid}">
						<label for="confirmPassword" class="col-sm-3 control-label">Confirm Password</label>
						<div class="col-sm-6">
							<input type="password" name="confirmPassword" class="form-control"
								ng-model="ctrl.teacher.user.confirmPassword" placeholder="Confirm password" ng-minlength="6"
								ng-maxlength="20" required title="Confirm password" confirm-password="ctrl.teacher.user.password">
						</div>
						<div class="col-sm-offset-3 col-sm-6">
							<div ng-show="changePasswordForm.password.$dirty && changePasswordForm.confirmPassword.$dirty" class="error">
								<span ng-show="changePasswordForm.confirmPassword.$error.match">Passwords don't match!</span>
							</div>
						</div>
					</div>
				</div>
			</form>
			
			<!-- Scripts -->
			<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular.min.js"></script>
			<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular-animate.min.js"></script>
			<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular-resource.min.js"></script>
			<script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.1.2.js"></script>
			<script src="resources/js/change-password.js"></script>
			<script src="resources/js/controller/ChangePasswordController.js"></script>
			<script src="resources/js/service/teacher-service.js"></script>
			<script src="resources/js/directive/ChangeEmailDirective.js"></script>
			<script src="resources/js/directive/ValidPasswordDirective.js"></script>
			<script src="resources/js/directive/ConfirmPasswordDirective.js"></script>
		</div>
	</body>
</html>
