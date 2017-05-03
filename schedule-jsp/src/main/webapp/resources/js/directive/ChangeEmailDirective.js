// Check the email in the DB, but ignore your current:
app.directive('changeEmail', ['$q', '$http', function($q, $http) {
	return {
		restrict : 'A',
		require : 'ngModel',
		link : function(scope, element, attributes, ctrl) {
			ctrl.$asyncValidators.changeEmail = function(modelValue, viewValue) {
				var value = modelValue || viewValue;
				return $http({
					method : 'HEAD', url : '/user/' + value + "/"
				}).then(function successCallback(response) {
					if(value==scope.oldEmail) {return true;}
					return $q.reject();
				}, function errorCallback(response) {
					return true;
				});
			};
		}
	};
}]);