// Check for existing email:
app.directive('existingEmail', ['$q', '$http', function($q, $http) {
	return {
		restrict : 'A',
		require : 'ngModel',
		link : function(scope, element, attributes, ctrl) {
			ctrl.$asyncValidators.existingEmail = function(modelValue,
					viewValue) {
				var value = modelValue || viewValue;
				return $http({
					method : 'HEAD',
					url : '/user/' + value + '/'
				}).then(function successCallback(response) {
					return $q.reject();
				}, function errorCallback(response) {
					return true;
				});
			};
		}
	};
}]);