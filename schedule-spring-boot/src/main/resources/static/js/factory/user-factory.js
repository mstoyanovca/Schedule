'use strict';
app.factory('User', [ '$resource', function($resource) {
	return $resource('/user/:id', {
		id : '@userId'
	}, {
		update : {
			method : 'PUT'
		},
		checkEmail : {
			method : 'HEAD',
			params : {
				email : '@email'
			},
			url : '/user/:email/'
		},
		resetPassword : {
			method : 'PUT',
			params : {
				email : '@email'
			},
			url : '/user/reset-password/:email/'
		}
	}, {
		stripTrailingSlashes : false
	});
} ]);