// Check for existing email:
'use strict';
app.directive('existingEmail', function ($q, User) {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function (scope, element, attrs, ctrl) {
			ctrl.$asyncValidators.existingEmail = function (modelValue, viewValue) {
				var email = modelValue || viewValue;
				return new User().$checkEmail({
					email : email
				}).then(function() {
					console.log(email + " is already registered");
					return $q.reject('exists');
				}, function() {
					console.log(email + " is available");
					return true;
				});
			};
		}
	};
});