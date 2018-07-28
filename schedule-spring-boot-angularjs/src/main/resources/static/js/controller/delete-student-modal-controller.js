'use strict';
app.controller('delete-student-modal-controller', ['$uibModalInstance', 'student',
	function($uibModalInstance, student) {
	
	var self = this;
	self.student = student;

	self.ok = function() {
		$uibModalInstance.close();
	};

	self.cancel = function() {
		$uibModalInstance.dismiss();
	};
}]);