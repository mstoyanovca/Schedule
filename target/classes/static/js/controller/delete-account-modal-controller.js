'use strict';
app.controller('delete-account-modal-controller', ['$uibModalInstance', function($uibModalInstance) {
	
	var self = this;

	self.ok = function() {
		$uibModalInstance.close();
	};

	self.cancel = function() {
		$uibModalInstance.dismiss();
	};
}]);