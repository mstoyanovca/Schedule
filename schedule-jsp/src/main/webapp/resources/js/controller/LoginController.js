app.controller('LoginController', [ '$scope', '$uibModal', 'Teacher', 'User',
		function($scope, $uibModal, Teacher, User) {

			var self = this;

			self.createTeacher = function() {
				// console.log('Opening the new teacher modal.');
				self.teacher = new Teacher();
				self.openRegisterModal();
			}

			self.saveTeacher = function() {
				self.teacher.$save(function() {
					// console.log('Saved a new teacher ', $scope.teacher.name);
					document.location.href = "/login?activation";
				});
			};

			self.resetPassword = function() {
				// console.log('Opening the reset password modal.');
				self.user = new User();
				self.openResetPasswordModal();
			}

			self.updatePassword = function() {
				self.user.$update(function() {
					// console.log('Sending password reset token to ' +
					// $scope.user.email);
					document.location.href = "/login?password";
				});
			};

			self.openRegisterModal = function(size) {
				var modalInstance = $uibModal.open({
					animation : true,
					templateUrl : 'resources/partials/teacher.html',
					controller : 'TeacherModalCtrl',
					size : size,
					resolve : {
						teacher : function() {
							return self.teacher;
						}
					}
				});
				modalInstance.result.then(function(teacher) {
					self.teacher = teacher;
					self.saveTeacher();
				});
			};

			self.openResetPasswordModal = function(size) {
				var modalInstance = $uibModal.open({
					animation : true,
					templateUrl : 'resources/partials/reset-password.html',
					controller : 'ResetPasswordModalCtrl',
					size : size,
					resolve : {
						user : function() {
							return self.user;
						}
					}
				});
				modalInstance.result.then(function(user) {
					self.user = user;
					self.updatePassword();
				});
			};
		} ]);
