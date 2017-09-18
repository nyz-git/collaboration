BlogCommentModule.controller('BlogCommentController', ['BlogCommentFactory','BlogFactory', '$http', '$scope','$routeParams', '$rootScope',function (blogCommentFactory, BlogFactory, $http, $scope, $routeParams, $rootScope) {

	var self = this;
	self.blogComment = { id: null, userId: '', blogComment: '' };
	self.singleBlog = {};

	self.submit = submit;
	self.likeBlog = likeBlog;
	self.remove = remove;
	self.reset = reset;

	getBlog();

	function getBlog(blogId) {
		var getBlogId=$routeParams.blogId;
		console.log(getBlogId);

		BlogFactory.getBlog(getBlogId)
			.then(
			function (d) {
				self.singleBlog = d;
				console.log(self.singleBlog);
			},
			function (errResponse) {
				console.error('error while fetching blog.')
			}
			);
	}

	function likeBlog(blogId) {
		var getBlogId=$routeParams.blogId;
		console.log(getBlogId);

		BlogFactory.likeBlog(getBlogId)
			.then(
			function (d) {
				self.singleBlog = d;
				console.log(d);
				getBlog();
			},
			function (errResponse) {
				console.error('Error while liking Blog');
			}
			);
	}


	function createBlogComment(blogComment, blogId) {
		blogCommentFactory.createBlogComment(blogComment, blogId)
			.then(
			function (d) {
				self.blogComment = d;
				console.log(self.blogComment);
				getBlog();
			},
			function (errResponse) {
				console.error('Error while creating blogComment');
			}
			);
	}


	function deleteBlogComment(id) {
		blogCommentFactory.deleteBlogComment(id)
			.then(
			getBlogComments,
			function (d) {
				self.blogComment = d;
			},
			function (errResponse) {
				console.error('Error while deleting User');
			}
			);
	}

	function submit() {
		if (self.blogComment.id == '' || self.blogComment.id == undefined) {
			console.log('Saving New blogComment', self.blogComment);
			self.blogComment.userId = $rootScope.userId;
			console.log($rootScope.userId);
			console.log(self.singleBlog.blogId);
			createBlogComment(self.blogComment, self.singleBlog.blog.blogId);
		} 
	}


	function remove(id) {
		console.log('id to be deleted', id);
		if (self.blogComment.id === id) {
			reset();
		}
		deleteBlogComment(id);
	}

	function reset() {
		self.blogComment = { id: null, userId: '', blogComment: '' };
		$scope.blogCommentForm.$setPristine(); //reset Form
	}

}]);