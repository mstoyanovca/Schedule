'use strict';

app.factory('Student', ['$resource', function($resource) {
	return $resource('/student/:id', {
		id : '@studentId'
	}, {
		update : {
			method : 'PUT'
		}
	}, {
		stripTrailingSlashes : false
	});
}]);