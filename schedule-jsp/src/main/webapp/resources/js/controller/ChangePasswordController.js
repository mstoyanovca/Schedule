'use strict';

app.controller('ChangePasswordController', [ '$scope', 'Teacher',
		function($scope, Teacher) {

			var self = this;
			self.teacher = new Teacher();

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

			self.changePassword = function() {
				self.teacher.$update(function() {
					// console.log('Updating teacher ', self.teacher.name);
					document.location.href = "/logout";
				});
			};
		} ]);
