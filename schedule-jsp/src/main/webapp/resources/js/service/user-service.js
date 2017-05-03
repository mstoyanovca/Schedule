'use strict';

app.factory('User', [ '$resource', function($resource) {
	return $resource('/user/:id/', {
		id : '@userId'
	}, {
		update : {
			method : 'PUT',
			params : {
				email : '@email'
			},
			url : '/user/:email/'
		}
	}, {
		stripTrailingSlashes : false
	});
} ]);