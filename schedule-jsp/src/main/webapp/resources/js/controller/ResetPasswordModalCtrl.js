'use-strict';

app.controller('ResetPasswordModalCtrl', [ '$scope', '$uibModalInstance',
		'user', function($scope, $uibModalInstance, user) {

			$scope.user = user;

			$scope.ok = function() {
				$uibModalInstance.close($scope.user);
			};

			$scope.cancel = function() {
				$uibModalInstance.dismiss('cancel');
			};
		} ]);
