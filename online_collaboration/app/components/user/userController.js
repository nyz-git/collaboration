UserModule.controller('UserController', ['$scope', '$rootScope', 'UserFactory', 'AuthenticationFactory', 'ForumFactory', 'UploadFactory', '$routeParams', '$timeout', function ($scope, $rootScope, UserFactory, AuthenticationFactory, ForumFactory, UploadFactory, $routeParams, $timeout) {
    var self = this;
    self.user = {};
    self.users = [];
    self.friendRequest = [];
    self.myFriends = [];
    self.client = {};
    self.request = {};
    self.submit = submit;
    self.userList = [];
    self.blogList = [];
    self.forumList = [];
    self.friendList = [];
    self.onlineFriends = [];
    self.forum = {};

    getUser = function () {
    
        getUserId = $rootScope.user.userId;
        console.log(getUserId);
        UserFactory.getUser(getUserId)
            .then(
            function (d) {
                self.client = d;
                console.log(self.client);
            },
            function (errResponse) {
                console.error('Error while updating User');
            }
            );
    }

    getUser();

    function updateUser(user, userId) {
        UserFactory.updateUser(user, userId)
            .then(
            function (d) {
                self.user = d;
                AuthenticationFactory.saveUser(user);
                AuthenticationFactory.loadUserFromCookie();
                console.log(self.user);
            },
            function (errResponse) {
                console.error('Error while updating User');
            }
            );
    }
    function submit() {
        if (self.client.userId != '' || self.client.userId != undefined) {
            updateUser(self.client, self.client.userId);
            console.log('User updated with id ', self.client.userId);
        }
    }

    self.picture = undefined;

    self.customer = AuthenticationFactory.loadUserFromCookie();
    console.log(self.customer);
    // the decached technique is used to see the updated image immediately with out page refresh
    self.customer.profileId = self.customer.profileId + '?decached=' + Math.random();

    // once the controller loads call the jQuery
    $timeout(function () {
        load();
    }, 100);

    // to upload the file    
    self.uploadFile = function () {
        if (self.picture == undefined) {
            return;
        }
        // self.picture will get the value from the directive created previously
        // it is bind to the HTML input  
        UploadFactory.uploadFile(self.picture)
            .then(
            function (response) {
                $rootScope.message = 'Profile picture updated successfully!';
                //message contains the profileId updated in the backend too
                self.customer.profileId = response.message + '?decached=' + Math.random();
                // update the controller user too
                $rootScope.user.profileId = response.message + '?decached=' + Math.random();
                // need to update the cookie value too
                AuthenticationFactory.saveUser($rootScope.user);
        
                console.log($rootScope.user);
                // hide the card panel by setting the rootScope.message as undefined
                $timeout(function () {
                    $rootScope.message = undefined;
                }, 2000);

            },
            function (error) {
                console.log(error);
            }
            )
    };

    function addFriend(friendId) {
        self.request.friendId = friendId;
        self.request.userId = $rootScope.user.userId;
        UserFactory.addFriend(self.request)
            .then(
            function (d) {
                self.request = d;
                console.log(self.user);
            },
            function (errResponse) {
                console.error('Error sending request User');
            }
            );
    }

    function getMyFriends() {
        UserFactory.getMyFriends($rootScope.user.userId)
            .then(
            function (d) {
                self.myFriends = d;
                console.log(self.user);
            },
            function (errResponse) {
                console.error('Error getting friends');
            }
            );
    }

    getMyFriends();

    function getOnlineFriends() {
        UserFactory.getOnlineFriends($rootScope.user.userId)
            .then(
            function (d) {
                self.onlineFriends = d;
                console.log(self.onlineFriends);
            },
            function (errResponse) {
                console.error('Error getting friends');
            }
            );
    }

    getOnlineFriends();

    function getRequests() {
        UserFactory.getRequests($rootScope.user.userId)
            .then(
            function (d) {
                self.friendRequest = d;
                console.log(self.user);
            },
            function (errResponse) {
                console.error('Error getting request');
            }
            );
    }

    getRequests();

    self.acceptFriendRequest = function (friendId) {
        
        self.request.friendId = $rootScope.user.userId;
        console.log(self.request.friendId);
        self.request.userId = friendId;
        console.log(self.request.userId);
        UserFactory.acceptFriendRequest(self.request)
            .then(
            function (d) {
                self.request = d;
                console.log(self.request);
                getRequests();
            },
            function (errResponse) {
                console.error('Error accepting request');
                console.log(errResponse);
            }
            );
    }

    self.rejectFriendRequest = function (friendId) {
    
        self.request.friendId = $rootScope.user.userId;
        console.log(self.request.friendId);
        self.request.userId = friendId;
        console.log(self.request.userId);
        UserFactory.rejectFriendRequest(self.request)
            .then(
            function (d) {
                self.request = d;
                console.log(self.request);
                getRequests();
            },
            function (errResponse) {
                console.error('Error rejecting request');
                console.log(errResponse);
            }
            );
    }

    function getLatestUsers() {
        UserFactory.getLatestUsers().
            then(function (data) {
                console.log(data);
                self.userList = data;
                if (self.userList.length <= 0) {
                    self.none = true;
                }
                self.failed = false;
            }, function (errResponse) {
                console.error(errResponse);
                self.failed = true;
            });

    }

    getLatestUsers();

    fetchLatestBlogs();


    function fetchLatestBlogs() {
        UserFactory
            .fetchLatestBlogs()
            .then(function (d) {
                self.blogList = d;
            }, function (errResponse) {
                console.error('Error while fetching the blogs');
            })
    }

    function fetchLatestForums(userId) {
        UserFactory.fetchLatestForums(userId)
            .then(function (d) {
                self.forumList = d;
                console.log(self.forumList)
            }, function (errResponse) {
                console.error('Error while fetching the forums');
            });
    }

    fetchLatestForums($rootScope.userId);

    function joinForum(forum) {
		ForumFactory.joinForum(forum)
			.then(
			function (d) {
				self.forum = d;
				console.log(self.forum);
			},
			function (errResponse) {
				console.error('Error while creating forum');
			}
			);
	}

    	self.sendForumRequest = function (forumId) {
		console.log(forumId);
		self.forum.forumId = forumId;
		console.log($rootScope.userId);
		self.forum.userId = $rootScope.userId;
		joinForum(self.forum);
	}

    function fetchLatestFriends() {
        UserFactory.fetchLatestFriends().
            then(function (data) {
                console.log(data);
                self.friendList = data;
                if (self.friendList.length <= 0) {
                    self.none = true;
                }
                self.failed = false;
            }, function (errResponse) {
                console.error(errResponse);
                self.failed = true;
            });

    }

    fetchLatestFriends();

}]);

