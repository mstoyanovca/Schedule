'use strict';
app.controller('student-controller',
		['$uibModal', 'Student', '$cookies', 'jwtHelper', '$http', '$location', 'Message',
		function($uibModal, Student, $cookies, jwtHelper, $http, $location, Message) {

			var self = this;
			self.students = [];
			self.teacherName = "";

			// Student CRUD operations:
			self.fetchAllStudents = function() {
				self.students = Student.query(function success() {
					console.log('Fetching all students.');
				});
			};

			self.create = function() {
				console.log('Creating a new student.');
				self.student = new Student({
					phones : [ {
						number : "",
						type : "other"
					} ]
				});
				self.openAddStudentModal();
			};

			self.save = function() {
				self.student.$save(function() {
					console.log('Saved a new student ', self.student.name);
					self.fetchAllStudents();
				});
			};

			self.edit = function(student) {
				console.log('Student to be edited: ', student.name);
				self.student = student;
				self.openAddStudentModal();
			};

			self.update = function() {
				self.student.$update(function() {
					console.log('Updated student ', self.student.name);
					self.fetchAllStudents();
				});
			};

			self.remove = function(student) {
				self.student = student;
				self.openDeleteStudentModal();
			};
			
			if($cookies.get("ACCESS-TOKEN")) {
				console.log("Access token is valid");
				$http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get("ACCESS-TOKEN");
				// JWT:
				var token = $cookies.get("ACCESS-TOKEN");
		        var payload = jwtHelper.decodeToken(token);
		        self.teacherName = payload.teacherName;
		        self.fetchAllStudents();
			} else {
				console.log("Access token has expired");
				$http({
					method: 'POST',
					url: '/refresh'
				}).then(function successCallback(response) {
					console.log("Refreshed the access token");
					$http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get("ACCESS-TOKEN");
					// JWT:
					var token = $cookies.get("ACCESS-TOKEN");
			        var payload = jwtHelper.decodeToken(token);
			        self.teacherName = payload.teacherName;
			        self.fetchAllStudents();
				}, function errorCallback(response) {
					console.log("Refresh token has expired");
					self.logout();
				});
			}

			self.logout = function() {
				$http.post('/logout', {}).success(function() {
					console.log("Logging out ......");
					Message.reset();
					Message.loggedOut = true;
					$location.path("/");
				}).error(function(data) {
					console.log("Logout failed");
					Message.reset();
				});
			};

			// add/edit Student modal:
			self.openAddStudentModal = function() {
				var modalInstance = $uibModal.open({
					templateUrl : 'views/student-modal.html',
					controller : 'student-modal-controller',
					controllerAs : '$ctrl',
					size : 'md',
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
				}, function(){
					self.students = Student.query();
				});
			};
			
			// delete student modal:
			self.openDeleteStudentModal = function() {
				self.obj = 'student';
				var modalInstance = $uibModal.open({
					templateUrl : 'views/delete-student-modal.html',
					controller : 'delete-student-modal-controller',
					controllerAs : '$ctrl',
					size : 'sm',
					resolve : {
						student : function() {
							return self.student;
						}
					}
				});
				modalInstance.result.then(function() {
					self.student.$delete(function() {
						console.log('Deleting student ', self.student.name);
						self.fetchAllStudents();
					});
				}, function(){
					console.log('Deleting student ' + self.student.name + ' canceled');
				});
			};
			
		} ]);






















