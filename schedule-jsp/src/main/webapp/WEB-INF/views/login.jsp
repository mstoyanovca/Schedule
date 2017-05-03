<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<title>Login</title>
		
		<link rel="icon" href="resources/images/favicon.ico" type="image/x-icon" sizes="16x16">
		<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="resources/css/login.css" type="text/css" />
		
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>
	
	<body ng-app="login">
		<div ng-controller="LoginController as ctrl" ng-cloak class="container-fluid ng-cloak">
		
			<!-- Header -->
			<div class="panel panel-default header">
				<h1>Music Lessons Schedule</h1>
			</div>

			<!-- Actual body content -->
			<div class="body-content">
				<!-- Left panel -->
				<div class="col-md-8 left">
					<h2>
						<i>Login:</i>
					</h2>

					<form:form action="" commandName="user" method="post" class="form-horizontal">
						
						<div class="form-group">
							<label for="email" class="col-sm-2 control-label">Email:</label>
							<div class="col-sm-4">
								<form:input type="email" path="email" id="email" class="form-control" placeholder="Email" />
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-sm-2 control-label">Password:</label>
							<div class="col-sm-4">
								<form:input type="password" path="password" id="password" class="form-control" placeholder="Password" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<c:if test="${param.error != null}">
									<p class="error">Access denied.</p>
								</c:if>
								<c:if test="${param.logout != null}">
									<p class="error">You have logged out successfully.</p>
								</c:if>
								<c:if test="${param.activation != null}">
									<p class="error">Check your email for activation instructions.</p>
								</c:if>
								<c:if test="${param.password != null}">
									<p class="error">Check your email for password change instructions.</p>
								</c:if>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div class="checkbox">
	                            	<label><input type="checkbox" id="rememberme" name="remember-me">Remember me</label>  
	                            </div>
	                        </div>
                        </div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-2">
								<button type="submit" name="button" class="btn btn-primary">Login</button>
							</div>
						</div>
					</form:form>
				</div>

				<!-- Aside panel -->
				<div class="col-md-4 right">
					<span>New users, create an account first.</span>
					<button type="button" class="btn btn-link" ng-click="ctrl.createTeacher()">Register</button>
					<p>
						Forgot your password?
						<button type="button" class="btn btn-link" ng-click="ctrl.resetPassword()">
							Reset password
						</button>
					</p>
					<p>
						<a href="mailto:mstoyanovca@gmail.com?Subject=Student%20request">
							Contact administartor
						</a>
					</p>
				</div>
			</div>

			<!-- Footer -->
			<div class="panel panel-default footer">
				<p class="autor">
					<b><i>Writen by Martin Stoyanov.</i></b>
				</p>
			</div>

			<!-- Register a new teacher modal -->
			<script type="text/ng-template" id="teacher.html">
        		<!-- Content in teacher.html -->
    		</script>

			<!-- Reset password modal -->
			<script type="text/ng-template" id="reset-password.html">
        		<!-- Content in reset-password.html -->
    		</script>

			<!-- Scripts -->
			<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular.min.js"></script>
			<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular-animate.min.js"></script>
			<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular-resource.min.js"></script>
			<script src="resources/js/login.js"></script>
			<script src="resources/js/controller/LoginController.js"></script>
			<script src="resources/js/controller/TeacherModalCtrl.js"></script>
			<script src="resources/js/controller/ResetPasswordModalCtrl.js"></script>
			<script src="resources/js/directive/ExistingEmailDirective.js"></script>
			<script src="resources/js/directive/ValidPasswordDirective.js"></script>
			<script src="resources/js/directive/ConfirmPasswordDirective.js"></script>
			<script src="resources/js/service/teacher-service.js"></script>
			<script src="resources/js/service/user-service.js"></script>
			<script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.1.2.js"></script>
		</div>
	</body>
</html>
