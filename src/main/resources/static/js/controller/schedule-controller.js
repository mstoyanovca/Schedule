'use strict';

angular.module('application').controller('schedule-controller',
	['$scope', '$uibModal', 'Lesson', '$cookies', '$http', 'jwtHelper', 'Message', '$location',
	function($scope, $uibModal, Lesson, $cookies, $http, jwtHelper, Message, $location) {
		
	var self = this;
	self.lessons = [];
	self.teacherName = "";
	self.active=0;
			
	// Day Of Week tabs:
	self.tabs = [
		{id: 0, title : 'Monday'},
		{id: 1, title : 'Tuesday'},
		{id: 2, title : 'Wednesday'},
		{id: 3, title : 'Thursday'},
		{id: 4, title : 'Friday'},
		{id: 5, title : 'Saturday'},
		{id: 6, title : 'Sunday'}
	];

	// DOW tabs:
	self.selectDOW = function(d) {
		self.dow = d.toUpperCase();
	};

			// Lesson CRUD operations:
			self.fetchAllLessons = function() {
				console.log('Fetching all lessons.');
				self.lessons = Lesson.query();
			};

			self.create = function() {
				self.dow = self.tabs[self.active].title.toUpperCase();
				var startTime;
				var endTime;
				if (self.dow == "SATURDAY" || self.dow == "SUNDAY") {
					startTime = new Date(1970, 1, 1, 9, 0);
					endTime = new Date(1970, 1, 1, 9, 30);
				} else {
					startTime = new Date(1970, 1, 1, 16, 0);
					endTime = new Date(1970, 1, 1, 16, 30);
				}
				self.lesson = new Lesson({
					startTime : startTime,
					endTime : endTime,
					dow : self.dow
				});
				self.openNewLessonModal();
			};

			self.save = function() {
				self.lesson.$save(function() {
					console.log('Saved a new lesson ', self.lesson.student.name);
					self.fetchAllLessons();
				});
			};

			self.edit = function(lesson) {
				console.log('Edit lessonId = ', lesson.lessonId);
				self.lesson = lesson;
				self.openNewLessonModal();
			};

			self.update = function() {
				console.log('Updated lesson with lessonId = ', self.lesson.lessonId);
				self.lesson.$update(function() {
					self.fetchAllLessons();
				});
			};

			self.remove = function(lesson) {
				self.lesson = lesson;
				self.openDeleteLessonModal();
			};
			
			if($cookies.get("ACCESS-TOKEN")) {
				console.log("Access token is valid");
				$http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get("ACCESS-TOKEN");
				// JWT:
				var token = $cookies.get("ACCESS-TOKEN");
		        var payload = jwtHelper.decodeToken(token);
		        self.teacherName = payload.teacherName;
		        self.fetchAllLessons();
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
			        self.fetchAllLessons();
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

			// new lesson modal:
			self.openNewLessonModal = function() {
				var modalInstance = $uibModal.open({
					templateUrl : 'views/lesson-modal.html',
					controller : 'lesson-modal-controller',
					size : 'md',
					resolve : {
						lesson : function() {
							return self.lesson;
						}
					}
				});
				modalInstance.result.then(function(lesson) {
					if (self.lesson.lessonId == null) {
						self.save();
					} else {
						self.update();
					}
				});
			};
			
			// delete lesson modal:
			self.openDeleteLessonModal = function() {
				var modalInstance = $uibModal.open({
					templateUrl : 'views/delete-lesson-modal.html',
					controller : 'delete-lesson-modal-controller',
					controllerAs: '$ctrl',
					size : 'sm',
					resolve : {
						lesson : function() {
							return self.lesson;
						}
					}
				});
				modalInstance.result.then(function() {
					self.lesson.$delete(function() {
						console.log('Deleted lessonId = ', self.lesson.lessonId);
						self.fetchAllLessons();
					});
				}, function(){
					console.log('Deleting lessonId = ', self.lesson.lessonId + ' canceled');
				});
			};
			
		}]);












