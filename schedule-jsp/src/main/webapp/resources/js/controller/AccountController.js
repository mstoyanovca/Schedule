'use strict';

app.controller('AccountController', [ '$scope', 'Teacher',
		function($scope, Teacher) {

			var self = this;

			self.findTeacher = function() {
				// console.log('Find a teacher with teacherId = ' +
				// $scope.teacherId);
				self.teacher = Teacher.get({
					id : $scope.teacherId
				}, function() {
					$scope.oldEmail = self.teacher.user.email;
				});
			};

			self.findTeacher();

			self.update = function() {
				self.teacher.$update(function() {
					// console.log('Updating teacher ', self.teacher.name);
					self.findTeacher();
				});
			};

			self.deleteTeacher = function() {
				if (confirm("Delete account?")) {
					self.teacher.$remove(function() {
						// console.log('Deleting teacher ',
						// self.teacher.teacherId);
						document.location.href = "/logout";
					});
				} else {
					// console.log('Deleting teacher with teacherId = ' +
					// self.teacher.teacherId + " cancelled.");
					return;
				}
			};

			self.schedule = function() {
				document.location.href = "/schedule";
			};

			self.students = function() {
				document.location.href = "/students";
			};

			self.logout = function() {
				document.location.href = "/logout";
			};

			self.changePassword = function() {
				document.location.href = "/change-password";
			};
		} ]);
