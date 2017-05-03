app
		.controller(
				'LessonModalCtrl',
				[
						'$scope',
						'$uibModalInstance',
						'lesson',
						'Student',
						function($scope, $uibModalInstance, lesson, Student) {

							$scope.lesson = lesson;
							$scope.students = Student.query();
							// Minute step for the time pickers:
							$scope.mstep = 15;

							$scope.ok = function() {
								$uibModalInstance.close($scope.lesson);
							};

							$scope.cancel = function() {
								$uibModalInstance.dismiss('cancel');
							};

							if ($scope.lesson.dow == "SATURDAY"
									|| $scope.lesson.dow == "SUNDAY") {
								$scope.minStartTime = new Date(1970, 1, 1, 9, 0);
								$scope.maxStartTime = new Date(1970, 1, 1, 21,
										0);
								$scope.minEndTime = new Date(1970, 1, 1, 9, 30);
								$scope.maxEndTime = new Date(1970, 1, 1, 21, 30);
							} else {
								$scope.minStartTime = new Date(1970, 1, 1, 16,
										0);
								$scope.maxStartTime = new Date(1970, 1, 1, 21,
										0);
								$scope.minEndTime = new Date(1970, 1, 1, 16, 30);
								$scope.maxEndTime = new Date(1970, 1, 1, 21, 30);
							}

							$scope.changedStart = function() {
								var dif = ($scope.lesson.endTime - $scope.lesson.startTime) / 60000;
								if (dif < 30) {
									var d = new Date($scope.lesson.startTime
											.getTime() + 1800000);
									$scope.lesson.endTime = d;
								}
							};

							$scope.changedEnd = function() {
								var dif = ($scope.lesson.endTime - $scope.lesson.startTime) / 60000;
								if (dif < 30) {
									var d = new Date($scope.lesson.endTime
											.getTime() - 1800000);
									$scope.lesson.startTime = d;
								}
							};
						} ]);
