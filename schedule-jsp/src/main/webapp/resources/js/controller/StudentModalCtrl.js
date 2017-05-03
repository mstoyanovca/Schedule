'use strict';

app.controller('StudentModalCtrl', [
		'$scope',
		'$uibModalInstance',
		'student',
		function($scope, $uibModalInstance, student) {

			$scope.student = student;

			$scope.ok = function() {
				$uibModalInstance.close($scope.student);
			};

			$scope.cancel = function() {
				$uibModalInstance.dismiss('cancel');
			};

			$scope.removePhone = function(phone) {
				if ($scope.student.phones.length > 1) {
					$scope.student.phones.splice($scope.student.phones
							.indexOf(phone), 1);
				}
			};

			$scope.addPhone = function() {
				$scope.student.phones.push({
					type : 'other'
				});
			};
		} ]);