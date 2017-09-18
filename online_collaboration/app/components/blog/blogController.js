BlogModule.controller('BlogController', ['BlogFactory', '$http', '$scope','$rootScope', '$location', function (BlogFactory, $http, $scope, $rootScope, $location) {

	var self = this;
	self.blogs = [];
	self.myBlogs = [];
	self.blog = { blogId: undefined, title: '', description: '', userId: '' };
	self.singleBlog = {};

	self.submit = submit;
	self.edit = edit;
	self.remove = remove;
	self.reset = reset;

	fetchAllBlogs();


	function fetchAllBlogs() {
		BlogFactory
			.fetchAllBlogs()
			.then(function (d) {
				self.blogs = d;
			}, function (errResponse) {
				console.error('Error while fetching the blogs');
			})
	}

	getMyBlogs($rootScope.userId);


	function getMyBlogs(userId) {
		BlogFactory
			.getMyBlogs(userId)
			.then(function (d) {
				self.myBlogs = d;
			}, function (errResponse) {
				console.error('Error while fetching the blogs');
			})
	}

	self.getBlog = function(blogId) {
		BlogFactory.getBlog(blogId)
			.then(
			function (d) {
				self.singleBlog = d;
				$location.path('/user/singleBlog');				
			},
			function (errResponse) {
				console.error('error while fetching blog.')
			}
			);

	}


	function createBlog(blog) {
		console.log(blog);
		BlogFactory.createBlog(blog)
			.then(
			function (d) {
				self.blog = d;
				console.log(d);
			},
			function (errResponse) {
				console.error('Error while creating Blog');
			}
			);
	}

	function updateBlog(blog, blogId) {
		BlogFactory.updateBlog(blog, blogId)
			.then(
			fetchAllBlogs,
			function (d) {
				self.blog = d;
			},
			function (errResponse) {
				console.error('Error while updating Blog');
			}
			);
	}

	function deleteBlog(blogId) {
		BlogFactory.deleteBlog(blogId)
			.then(
			fetchAllUsers,
			function (d) {
				self.blog = d;
			},
			function (errResponse) {
				console.error('Error while deleting blog');
			}
			);
	}

	function submit() {
		if (self.blog.blogId == '' || self.blog.blogId == undefined) {
			console.log('Saving New Blog', self.blog);
			self.blog.userId = $rootScope.userId;
			console.log($rootScope.userId);
			createBlog(self.blog);
		} else {
			updateBlog(self.blog, self.blog.blogId);
			console.log('Blog updated with id ', self.blog.blogId);
		}
	}

	function edit(blogId) {
		console.log('id to be edited', blogId);
		for (var i = 0; i < self.Blogs.length; i++) {
			if (self.blogs[i].blogId === blogId) {
				self.blog = angular.copy(self.blogs[i]);
				break;
			}
		}
	}

	function remove(blogId) {
		console.log('id to be deleted', blogId);
		if (self.blog.blogId === blogId) {
			reset();
		}
		deleteBlog(blogId);
	}

	function reset() {
		self.blog = { blogId: null, title: '', description: '', likes: '', status: '' };
		$scope.blogForm.$setPristine(); //reset Form
	}

}]);