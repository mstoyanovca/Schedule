// Compare the password and confirmPassword fields for a match:
app.directive('confirmPassword', function() {
	return {
		restrict : "A",
		require : "ngModel",
		scope : {
			password : "=confirmPassword"
		},
		link : function(scope, element, attribute, ngModel) {
			ngModel.$validators.match = function(modelValue) {
				return modelValue == scope.password;
			};
			scope.$watch("password", function() {
				ngModel.$validate();
			});
		}
	};
});