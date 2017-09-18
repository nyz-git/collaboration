ForumCommentModule.controller('ForumCommentController', ['$rootScope', '$scope', '$http', '$routeParams', 'ForumFactory', 'ForumCommentFactory', function ($rootScope, $scope, $http, $routeParams, ForumFactory, ForumCommentFactory) {

	var self = this;
	self.singleForum = {};
	self.request = {userId: '', forumId: ''};
	self.members = [];
	self.forumRequests = [];
	self.forumComment = { id: null, userId: '', forumComment: '' };

	self.submit = submit;
	self.reset = reset;

	getForum();
	getForumRequest();
	getForumMember();

	function getForum() {
		var getForumId = $routeParams.forumId;
		console.log(getForumId);

		ForumFactory.getForum(getForumId)
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


	function getForumRequest() {
		var getForumId = $routeParams.forumId;
		console.log(getForumId);
		ForumFactory.getForumRequest(getForumId)
			.then(
			function (d) {
				self.forumRequests = d;
				console.log('Request' + self.forumRequests);
				getForum();
			},
			function (errResponse) {
				console.error('error while fetching blog.')
			}
			);
	}

	function getForumMember() {
		var getForumId = $routeParams.forumId;
		console.log(getForumId);
		ForumFactory.getForumMember(getForumId)
			.then(
			function (d) {
				self.members = d;
				console.log('member' + self.members);
			},
			function (errResponse) {
				console.error('error while fetching blog.')
			}
			);
	}


	function createforumComment(forumComment, forumId) {
		ForumCommentFactory.createforumComment(forumComment, forumId)
			.then(
			function (d) {
				self.forumComment = d;
				getForumRequest();
				getForum();
				console.log(d);
			},
			function (errResponse) {
				console.error('Error while creating forumComment');
			}
			);
	}

	self.approveForumRequest = function(userId){
		self.request.userId = userId;
		console.log(self.request.userId);
		self.request.forumId = self.singleForum.forum.forumId;
		console.log(self.request.forumId);
		ForumCommentFactory.approveForumRequest(self.request)
			.then(
			function (d) {
				self.forumComment = d;
				getForumRequest();
				console.log(d);
			},
			function (errResponse) {
				console.error('Error while creating forumComment');
			}
			);
	}

	self.disapproveForumRequest = function(userId){
		self.request.userId = userId;
		console.log(self.request.userId);
		self.request.forumId = self.singleForum.forum.forumId;
		console.log(self.request.forumId);
		ForumCommentFactory.disapproveForumRequest(self.request)
			.then(
			function (d) {
				self.forumComment = d;
				getForum();
				getForumRequest();
				console.log(d);
			},
			function (errResponse) {
				console.error('Error while creating forumComment');
			}
			);
	}

	function submit() {
		if (self.forumComment.id == '' || self.forumComment.id == undefined) {
			console.log('Saving New forumComment', self.forumComment);
			self.forumComment.userId = $rootScope.userId;
			console.log($rootScope.userId);
			console.log(self.singleForum.forum.forumId);
			createforumComment(self.forumComment, self.singleForum.forum.forumId);
		}
	}


	function reset() {
		self.forumComment = { id: null, userId: '', forumComment: '' };
		$scope.forumCommentForm.$setPristine(); //reset Form
	}

}]);
