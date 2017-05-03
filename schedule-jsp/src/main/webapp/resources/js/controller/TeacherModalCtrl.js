'use-strict';

// Register new teacher form, wrapped in a modal:
app.controller('TeacherModalCtrl', [ '$scope', '$uibModalInstance', 'teacher',
		function($scope, $uibModalInstance, teacher) {

			$scope.teacher = teacher;

			$scope.ok = function() {
				$uibModalInstance.close($scope.teacher);
			};

			$scope.cancel = function() {
				$uibModalInstance.dismiss('cancel');
			};
		} ]);