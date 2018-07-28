'use strict';
app.controller('student-modal-controller', ['$uibModalInstance', 'student',
	function($uibModalInstance, student) {
		
		var self = this;
		self.student = student;

		self.ok = function() {
			$uibModalInstance.close(self.student);
		};

		self.cancel = function() {
			$uibModalInstance.dismiss();
		};

		self.removePhone = function(phone) {
			if (self.student.phones.length > 1) {
				self.student.phones.splice(self.student.phones.indexOf(phone), 1);
			}
		};

		self.addPhone = function() {
			self.student.phones.push({
				type : 'other'
			});
		};
}]);