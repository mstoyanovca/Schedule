'use strict';

app.factory('Teacher', ['$resource', function($resource) {
	return $resource('/teacher/:id', {
		id : '@teacherId'
	}, {
		update : {
			method : 'PUT'
		}
	}, {
		stripTrailingSlashes : false
	});
}]);