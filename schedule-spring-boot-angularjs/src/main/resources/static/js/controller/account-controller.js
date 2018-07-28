'use strict';
app.controller('account-controller', 
	['Teacher', '$cookies', '$http', 'jwtHelper', 'Message', '$location', '$uibModal',
	function(Teacher, $cookies, $http, jwtHelper, Message, $location, $uibModal) {

	var self = this;
	self.isCollapsed = false;
	self.teacher = new Teacher();
	self.teacherId = 0;
	self.teacherName = "";
	self.oldEmail = "";

	self.findTeacher = function() {
		console.log('Find a teacher with teacherId = ' + self.teacherId);
		self.teacher = Teacher.get({
			id : self.teacherId
		}, function() {
			self.teacherName = self.teacher.name;
			self.oldEmail = self.teacher.user.email;
		});
	};

	self.update = function() {
		self.teacher.$update(function() {
			console.log('Updating teacher ', self.teacher.name);
			self.logout();
		});
	};

	self.delete = function() {
		self.openDeleteAccountModal();
	};

	if($cookies.get("ACCESS-TOKEN")) {
		console.log("Access token is valid");
		$http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get("ACCESS-TOKEN");
		// JWT:
		var token = $cookies.get("ACCESS-TOKEN");
        var payload = jwtHelper.decodeToken(token);
        self.teacherId = payload.teacherId;
        self.findTeacher();
	} else {
		console.log("Access token has expired");
		$http({
			method: 'POST',
			url: '/refresh'
		}).then(function successCallback(response) {
			console.log("Refreshed the access token");
			$http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get("ACCESS-TOKEN");
			// JWT:
			var token = $cookies.get("ACCESS-TOKEN");
	        var payload = jwtHelper.decodeToken(token);
	        self.teacherId = payload.teacherId;
	        self.findTeacher();
		}, function errorCallback(response) {
			console.log("Refresh token has expired");
			self.logout();
		});
	}
	
	self.logout = function() {
		$http.post('/logout', {}).success(function() {
			console.log("Logging out ......");
			Message.reset();
			Message.loggedOut = true;
			$location.path("/");
		}).error(function(data) {
			console.log("Logout failed");
			Message.reset();
		});
	};
	
	self.openDeleteAccountModal = function() {
		var modalInstance = $uibModal.open({
			templateUrl : 'views/delete-account-modal.html',
			controller : 'delete-account-modal-controller',
			controllerAs: '$ctrl',
			size : 'sm'
		});
		modalInstance.result.then(function() {
			self.teacher.$remove(function() {
				console.log('Deleting teacherId = ', self.teacher.teacherId);
				self.logout();
			});
		}, function(){
			console.log('Deleting teacherId = ', self.teacher.teacherId + ' canceled');
		});
	};
}]);
















