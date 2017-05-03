'use strict';

app.factory('Lesson', ['$resource', function($resource) {
	return $resource('/lesson/:id', {
		id : '@lessonId'
	}, {
		update : {
			method : 'PUT'
		}
	}, {
		stripTrailingSlashes : false
	});
}]);