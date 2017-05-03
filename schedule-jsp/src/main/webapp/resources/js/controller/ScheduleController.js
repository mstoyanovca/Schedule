'use strict';

app.controller('ScheduleController', [
		'$scope',
		'$uibModal',
		'Lesson',
		function($scope, $uibModal, Lesson) {

			var self = this;
			self.lessons = [];
			// Options for the settings select element:
			self.options = [ {
				id : '1',
				name : 'Account'
			}, {
				id : '2',
				name : 'Logout'
			} ];
			// Day Of Week tabs:
			self.tabs = [ {
				title : 'Monday'
			}, {
				title : 'Tuesday'
			}, {
				title : 'Wednesday'
			}, {
				title : 'Thursday'
			}, {
				title : 'Friday'
			}, {
				title : 'Saturday'
			}, {
				title : 'Sunday'
			}, ];

			// Buttons:
			self.students = function() {
				document.location.href = "/students";
			}

			self.settings = function(option) {
				if (option.name !== "") {
					document.location.href = "/" + option.name.toLowerCase();
				}
			}

			// DOW tabs:
			self.selectDOW = function(d) {
				self.dow = d.toUpperCase();
			};

			// Lesson CRUD operations:
			self.fetchAllLessons = function() {
				// console.log('Fetching all lessons.');
				self.lessons = Lesson.query();
			};

			self.fetchAllLessons();

			self.create = function() {
				// console.log('Creating a new lesson.');
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
				self.open();
			};

			self.save = function() {
				self.lesson.$save(function() {
					// console.log('Saved a new lesson ',
					// self.lesson.student.name);
					self.fetchAllLessons();
				});
			};

			self.edit = function(lesson) {
				// console.log('Edit lesson with lessonId = ', lesson.lessonId);
				self.lesson = lesson;
				self.open();
			};

			self.update = function() {
				// console.log('Updated lesson with lessonId = ',
				// self.lesson.lessonId);
				self.lesson.$update(function() {
					self.fetchAllLessons();
				});
			};

			self.remove = function(lesson) {
				self.lesson = lesson;
				if (confirm("Delete lesson with " + self.lesson.student.name
						+ "?")) {
					self.lesson.$delete(function() {
						// console.log('Deleted lesson with lessonId = ',
						// self.lesson.lessonId);
						self.fetchAllLessons();
					});
				}
			};

			// New Lesson modal:
			self.open = function(size) {
				var modalInstance = $uibModal.open({
					templateUrl : 'resources/partials/lesson.html',
					controller : 'LessonModalCtrl',
					size : size,
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
		} ]);
