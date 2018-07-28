'use strict';

var app = angular.module('application',
		['ngRoute', 'angularCSS', 'ngResource', 'ngCookies', 'angular-jwt', 'ngAnimate', 
			'ui.bootstrap', 'ngTouch']);

app.config(function($routeProvider, $httpProvider) {
	$routeProvider.when('/', {
		templateUrl : 'views/login.html',
		css: "css/login.css",
		controller: 'login-controller',
		controllerAs: 'ctrl'
	}).when('/activate/:token', {
		template: " ",
		controller: 'activation-controller'
	}).when('/change-password/:token', {
		templateUrl: "views/change-password.html",
		css: "css/login.css",
		controller: 'change-password-controller',
		controllerAs : 'ctrl'
	}).when('/schedule', {
		templateUrl : 'views/schedule.html',
		css: "css/schedule.css",
		controller : 'schedule-controller',
		controllerAs : 'ctrl'
	}).when('/students', {
		templateUrl : 'views/students.html',
		css: "css/students.css",
		controller : 'student-controller',
		controllerAs : 'ctrl'
	}).when('/account', {
		templateUrl : 'views/account.html',
		css: "css/account.css",
		controller : 'account-controller',
		controllerAs : 'ctrl'
	}).otherwise('/');
});

app.controller('activation-controller', ['$routeParams', '$http', 'Message', '$location',
	function($routeParams, $http, Message, $location) {
	
	var token = $routeParams.token;
	
	$http({
		method: 'GET',
		url: '/account/activate/' + token
	}).then(function success(response) {
		console.log("Account activated successfully");
		Message.reset();
		Message.activated = true;
		$location.path("/");
	}, function error(response) {
		console.log("Error activating account");
		Message.reset();
		Message.invalidToken = true;
		$location.path("/");
	});
}]);

app.controller('change-password-controller', ['$routeParams', '$http', '$location', 'Teacher', 'User',
	function($routeParams, $http, $location, Teacher, User) {
	
	var self = this;
	self.teacher = new Teacher();
	self.success = false;
	self.error = false;
	
	// login with a password reset token:
	$http({
		method: 'GET',
		url: '/login/' + $routeParams.token
	}).then(function success(response) {
		self.teacher = response.data;
		self.teacher.user.password = "";
	}, function error(response) {
		self.error = true;
	});
	
	self.changePwd = function() {
		User.update({id:self.teacher.user.userId}, self.teacher.user, function success() {
			self.success = true;
			$http.post('/logout', {});
		});
	};
}]);

app.factory('Message', function() {
	
	var messages = {activated: false,
				    invalidToken: false,
				    loggedOut: false};
	
	messages.reset = function() {
		messages.activated = false;
		messages.loggedOut = false;
		messages.invalidToken = false;
	}
	
	return messages;
});

















