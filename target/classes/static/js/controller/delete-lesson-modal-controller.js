'use strict';
app.controller('delete-lesson-modal-controller', 
		['$uibModalInstance', 'lesson', function($uibModalInstance, lesson) {
	
	var self = this;
	self.lesson = lesson;

	self.ok = function() {
		$uibModalInstance.close();
	};

	self.cancel = function() {
		$uibModalInstance.dismiss();
	};
}]);