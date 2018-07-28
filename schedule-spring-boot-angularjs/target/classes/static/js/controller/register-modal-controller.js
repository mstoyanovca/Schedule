'use-strict';
app.controller('register-modal-controller', [ '$uibModalInstance', 'teacher',
	function($uibModalInstance, teacher) {
	
		var self = this;
		self.teacher = teacher;

		self.ok = function() {
			$uibModalInstance.close(self.teacher);
		};

		self.cancel = function() {
			$uibModalInstance.dismiss();
		};
} ]);