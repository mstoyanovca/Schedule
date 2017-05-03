'use-strict';
app.controller('reset-password-modal-controller', [ '$uibModalInstance', 'user',
	function($uibModalInstance, user) {
		
		var self = this;
		self.user = user;

		self.ok = function() {
			$uibModalInstance.close(self.user);
		};

		self.cancel = function() {
			$uibModalInstance.dismiss();
		};
}]);
