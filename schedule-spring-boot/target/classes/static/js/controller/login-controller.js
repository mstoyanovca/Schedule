'use strict';
app.controller('login-controller',
	['$http', '$location', 'Message', '$uibModal', 'Teacher', 'User',
	function($http, $location, Message, $uibModal, Teacher, User) {
	
	var self = this;
	self.user = {};
	self.teacher = new Teacher();
	self.user = new User();
	self.teacher.user = new User();
	self.message = Message;
	self.error = false;
	self.activate = false;
	self.changePwd = false;
	self.emailNotReg = false;
	
	self.login = function() {
		self.error = false;
		self.activate = false;
		self.changePwd = false;
		self.emailNotReg = false;
		self.message.reset();
		$http({
			method: 'POST',
			url: '/login',
			data: self.user
		}).then(function successCallback() {
			$location.path("/schedule");
		}, function errorCallback() {
			console.log("User not found");
			self.error = true;
		});
	};
	
	// SSO:
	$http.get("/sso/user").success(function(data) {
		if(typeof(data.id) !== "undefined") {
			var id = parseInt(data.id);
			console.log('Social SSO id = ', id);
			$http({
				method: 'GET',
				url: '/ssologin'
			}).then(function successCallback() {
				$location.path("/schedule");
			});		
		} else {
			console.log("Not logged with social SSO");
		}
	});
	
	// register modal:
	self.register = function() {
		console.log('Opening the register modal');
		self.error = false;
		self.activate = false;
		self.changePwd = false;
		self.emailNotReg = false;
		self.message.reset();
		self.openRegisterModal();
	};
	
	self.openRegisterModal = function() {
		var modalInstance = $uibModal.open({
			templateUrl : 'views/register-modal.html',
			controller : 'register-modal-controller',
			controllerAs: '$ctrl',
			size : 'md',
			resolve : {
				teacher : function() {
					return self.teacher;
				}
			}
		});
		modalInstance.result.then(function(teacher) {
			self.teacher = teacher;
			self.teacher.$save(function success() {
				console.log('Saved a new teacher', self.teacher.name);
				self.activate = true;
			});
		});
	};
	
	self.resetPwd = function() {
		console.log('Opening the reset password modal');
		self.error = false;
		self.activate = false;
		self.changePwd = false;
		self.emailNotReg = false;
		self.message.reset();
		self.openResetPasswordModal();
	};
	
	self.openResetPasswordModal = function() {
		var modalInstance = $uibModal.open({
			templateUrl : 'views/reset-password-modal.html',
			controller : 'reset-password-modal-controller',
			controllerAs : 'ctrl',
			size : 'md',
			resolve : {
				user : function() {
					return self.user;
				}
			}
		});
		modalInstance.result.then(function(user) {
			self.user = user;
			self.user.$resetPassword({email : self.user.email}, function success() {
				console.log('Sending password reset token to ' + self.user.email);
				self.changePwd = true;
			}, function error() {
				console.log('Email ' + self.user.email + " is not registered");
				self.emailNotReg = true;
			});
		});
	};
}]);














