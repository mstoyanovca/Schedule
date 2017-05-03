'use strict';

app.controller('StudentController', [ '$scope', '$uibModal', 'Student',
		function($scope, $uibModal, Student) {

			var self = this;
			self.students = [];
			// Options for the settings select:
			self.options = [ {
				id : '1',
				name : 'Account'
			}, {
				id : '2',
				name : 'Logout'
			} ];

			// Buttons:
			self.schedule = function() {
				document.location.href = "/schedule";
			}

			self.settings = function(option) {
				if (option.name !== "") {
					document.location.href = "/" + option.name.toLowerCase();
				}
			}

			// Student CRUD operations:
			self.fetchAllStudents = function() {
				// console.log('Fetching all students.');
				self.students = Student.query();
			};

			self.fetchAllStudents();

			self.create = function() {
				// console.log('Creating a new student.');
				self.student = new Student({
					phones : [ {
						number : "",
						type : "other"
					} ]
				});
				self.openModal();
			};

			self.save = function() {
				self.student.$save(function() {
					// console.log('Saved a new student ', self.student.name);
					self.fetchAllStudents();
				});
			};

			self.edit = function(student) {
				// console.log('Student to be edited: ', student.name);
				self.student = student;
				self.openModal();
			};

			self.update = function() {
				// console.log('Updated student ', self.student.name);
				self.student.$update(function() {
					self.fetchAllStudents();
				});
			};

			self.remove = function(student) {
				self.student = student;
				if (confirm("Delete " + self.student.name + "?")) {
					self.student.$delete(function() {
						// console.log('Deleted student ', self.student.name);
						self.fetchAllStudents();
					});
				}
			};

			// Add/edit Student modal:
			self.openModal = function(size) {
				var modalInstance = $uibModal.open({
					templateUrl : 'resources/partials/student.html',
					controller : 'StudentModalCtrl',
					size : size,
					resolve : {
						student : function() {
							return self.student;
						}
					}
				});

				modalInstance.result.then(function(student) {
					if (self.student.studentId == null) {
						self.save();
					} else {
						self.update();
					}
				});
			};
		} ]);
