
ForurmModule.controller('ForumController', ['ForumFactory', '$http', '$scope', '$rootScope', '$routeParams', function (ForumFactory, $http, $scope, $rootScope, $routeParams) {

	var self = this;
	self.forums = [];
	self.adminForum = [];
	self.userForum = [];
	self.forum = { forumId: undefined, userId: '', name: '' };
	self.request = {};

	self.submit = submit;
	self.edit = edit;
	self.remove = remove;
	self.reset = reset;
	self.getForumMember = getForumMember;
	self.sendForumRequest = sendForumRequest;
	self.getForum = getForum;
	self.createForum = createForum;
	self.updateForum = updateForum;
	self.deleteForum = deleteForum;

	function FetchAllForums(userId) {
		ForumFactory.fetchAllForums(userId)
			.then(function (d) {
				self.forums = d;
				console.log(self.forums)
			}, function (errResponse) {
				console.error('Error while fetching the forums');
			});
	}

	FetchAllForums($rootScope.user.userId);

	function fetchAdminForums(userId) {
		ForumFactory.fetchAdminForums(userId)
			.then(function (d) {
				self.adminForum = d;
				console.log(self.forums)
			}, function (errResponse) {
				console.error('Error while fetching the forums');
			});
	}

	fetchAdminForums($rootScope.user.userId);

	function fetchUserForums(userId) {
		ForumFactory.fetchUserForums(userId)
			.then(function (d) {
				self.userForum = d;
				console.log(self.forums)
			}, function (errResponse) {
				console.error('Error while fetching the forums');
			});
	}

	fetchUserForums($rootScope.user.userId);

	function getForum(forumId) {
		ForumFactory.getForum(forumId)
			.then(
			function (d) {
				self.forum = d;
			},
			function (errResponse) {
				console.error('error while fetching forum.')
			}
			);

	}

	function getForumMember(forumId) {
		var getForumId=$routeParams.forumId;
		console.log(forumId);

		ForumFactory.getForum(forumId)
			.then(
			function (d) {
				self.singleForum = d;
				console.log(self.singleForum);
			},
			function (errResponse) {
				console.error('error while fetching blog.')
			}
			);
	}

	function createForum(forum) {
		ForumFactory.createForum(forum)
			.then(
			function (d) {
				self.forum = d;
				console.log(self.forum)
			},
			function (errResponse) {
				console.error('Error while creating forum');
			}
			);
	}

	function joinForum(forum) {
		ForumFactory.joinForum(forum)
			.then(
			function (d) {
				self.forum = d;
				console.log(self.forum);
				FetchAllForums($rootScope.userId);
			},
			function (errResponse) {
				console.error('Error while creating forum');
			}
			);
	}

	function updateForum(forum, forumId) {
		ForumFactory.updateForum(forum, forumId)
			.then(
			function (d) {
				self.forum = d;
			},
			function (errResponse) {
				console.error('Error while updating forum');
			}
			);
	}

	function deleteForum(forumId) {
		ForumFactory.deleteForum(forumId)
			.then(
			function (d) {
				self.forum = d;
			},
			function (errResponse) {
				console.error('Error while deleting forum');
			}
			);
	}

	function sendForumRequest(forumId) {
		console.log(forumId);
		self.request.forumId = forumId;
		console.log($rootScope.userId);
		self.request.userId = $rootScope.userId;
		joinForum(self.request);
	}

	function submit() {
		if (self.forum.forumId == '' || self.forum.forumId == undefined) {
			console.log($rootScope.userId);
			self.forum.userId = $rootScope.userId;
			console.log('Saving New forum', self.forum);
			createForum(self.forum);
		} else {
			updateforum(self.forum, self.forum.forumId);
			console.log('forum updated with id ', self.forum.forumId);
		}
	}

	function edit(forumId) {
		console.log('id to be edited', forumId);
		for (var i = 0; i < self.forums.length; i++) {
			if (self.forums[i].forumId === forumId) {
				self.forum = angular.copy(self.forums[i]);
				break;
			}
		}
	}

	function remove(forumId) {
		console.log('id to be deleted', forumId);
		if (self.forum.forumId === forumId) {
			reset();
		}
		deleteforum(forumId);
	}

	function reset() {
		self.forum = { forumId: '', userId: '', name: '', categoryId: '', status: '' };
		$scope.forumForm.$setPristine(); //reset Form
	}

}]);