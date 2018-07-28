// Validate password:
app.directive('validPassword', ['$q', function($q) {
	return {
		restrict : 'A',
		require : 'ngModel',
		link : function(scope, element, attributes, ctrl) {
			var regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#\$%\^\&\*_\+\-=:\|;\<\>\?,\.])[a-zA-Z0-9~!@#\$%\^\&\*_\+\-=:\|;\<\>\?,\.]{6,20}$/;
			ctrl.$validators.validPassword = function (modelValue, viewValue) {
		        if (regex.test(viewValue)) {
		          return true
		        }
		        return false;
		      };
		}
	};
}]);