window.routes =
    {
        "/about": {
            templateUrl: 'app/components/page/about.html',
            controller: 'aboutController',
            controllerAs: 'aboutCtrl',
            requireLogin: false,
            roles: ['GUEST', 'USER', 'ADMIN']
        },

        "/contact": {
            templateUrl: 'app/components/page/contact.html',
            controller: 'contactController',
            controllerAs: 'contactCtrl',
            requireLogin: false,
            roles: ['GUEST', 'USER', 'ADMIN']
        },
        

        "/user/home": {
            templateUrl: 'app/components/user/home.html',
            controller: 'UserController',
            controllerAs: 'userCtrl',
            requireLogin: true,
            roles: ['USER', 'ADMIN']
        },

        "/admin/pendingBlogs": {
            templateUrl: 'app/components/blog/acceptBlog.html',
            controller: 'AdminController',
            controllerAs: 'adminCtrl',
            requireLogin: true,
            roles: ['ADMIN']
        },

        "/admin/pendingUsers": {
            templateUrl: 'app/components/user/acceptUser.html',
            controller: 'AdminController',
            controllerAs: 'adminCtrl',
            requireLogin: true,
            roles: ['ADMIN']
        },

        "/myFriends": {
            templateUrl: 'app/components/user/myFriends.html',
            controller: 'UserController',
            controllerAs: 'userCtrl',
            requireLogin: true,
            roles: ['ADMIN', 'USER']
        },

        "/friendRequests": {
            templateUrl: 'app/components/user/friendRequests.html',
            controller: 'UserController',
            controllerAs: 'userCtrl',
            requireLogin: true,
            roles: ['ADMIN', 'USER']
        },

        "/approvedUsers": {
            templateUrl: 'app/components/user/approvedUser.html',
            controller: 'AdminController',
            controllerAs: 'adminCtrl',
            requireLogin: true,
            roles: ['ADMIN', 'USER']
        },

        "/admin/blockUsers": {
            templateUrl: 'app/components/user/blockUser.html',
            controller: 'AdminController',
            controllerAs: 'adminCtrl',
            requireLogin: true,
            roles: ['ADMIN']
        },

        "/user/createBlog": {
            templateUrl: 'app/components/blog/createBlog.html',
            controller: 'BlogController',
            controllerAs: 'blogCtrl',
            requireLogin: true,
            roles: ['USER']
        },

        "/user/viewBlog": {
            templateUrl: 'app/components/blog/viewBlog.html',
            controller: 'BlogController',
            controllerAs: 'blogCtrl',
            requireLogin: true,
            roles: ['USER', 'ADMIN']
        },

        "/user/myBlogs": {
            templateUrl: 'app/components/blog/myBlogs.html',
            controller: 'BlogController',
            controllerAs: 'blogCtrl',
            requireLogin: true,
            roles: ['USER']
        },

        "/user/viewBlog/:blogId": {
            templateUrl: 'app/components/blog/singleBlog.html',
            controller: 'BlogCommentController',
            controllerAs: 'blogCommentCtrl',
            requireLogin: true,
            roles: ['USER']
        },

        "/viewForums": {
            templateUrl: 'app/components/forum/viewForum.html',
            controller: 'ForumController',
            controllerAs: 'forumCtrl',
            requireLogin: true,
            roles: ['USER', 'ADMIN']
        },

        "/admin/createForum": {
            templateUrl: 'app/components/forum/createForum.html',
            controller: 'ForumController',
            controllerAs: 'forumCtrl',
            requireLogin: true,
            roles: ['ADMIN']
        },

        "/admin/myForums": {
            templateUrl: 'app/components/forum/adminForum.html',
            controller: 'ForumController',
            controllerAs: 'forumCtrl',
            requireLogin: true,
            roles: ['ADMIN']
        },

        "/user/myForums": {
            templateUrl: 'app/components/forum/userForum.html',
            controller: 'ForumController',
            controllerAs: 'forumCtrl',
            requireLogin: true,
            roles: ['USER']
        },

        "/forum/members/:forumId": {
            templateUrl: 'app/components/user/viewMember.html',
            controller: 'ForumCommentController',
            controllerAs: 'forumCommentCtrl',
            requireLogin: true,
            roles: ['USER', 'ADMIN']
        },

        "/viewForum/:forumId": {
            templateUrl: 'app/components/forum/singleForum.html',
            controller: 'ForumCommentController',
            controllerAs: 'forumCommentCtrl',
            requireLogin: true,
            roles: ['USER', 'ADMIN']
        },

        "/forum/requests/:forumId": {
            templateUrl: 'app/components/user/viewForumRequest.html',
            controller: 'ForumCommentController',
            controllerAs: 'forumCommentCtrl',
            requireLogin: true,
            roles: ['ADMIN']
        },

        "/editProfile/:userId": {
            templateUrl: 'app/components/user/editProfile.html',
            controller: 'UserController',
            controllerAs: 'userCtrl',
            requireLogin: true,
            roles: ['USER', 'ADMIN']
        },


        "/chat": {
            templateUrl: 'app/components/chat/chat.html',
            controller: 'ChatController',
            controllerAs: 'ChatCtrl',
            requireLogin: true,
            roles: ['USER', 'ADMIN']
        },

        "/login": {
            templateUrl: 'app/components/authentication/login.html',
            controller: 'AuthenticationController',
            controllerAs: 'authCtrl',
            requireLogin: false,
            roles: ['GUEST']
        },

        "/viewUser": {
            templateUrl: 'app/components/user/userView.html',
            controller: 'AuthenticationController',
            controllerAs: 'authCtrl',
            requireLogin: true,
            roles: ['USER', 'ADMIN']
        },

        "/register": {
            templateUrl: 'app/components/authentication/register.html',
            controller: 'AuthenticationController',
            controllerAs: 'authCtrl',
            requireLogin: false,
            roles: ['GUEST']
        },
        "/error": {
            templateUrl: 'app/components/authentication/error.html',
            controller: 'AuthenticationController',
            controllerAs: 'authCtrl',
            requireLogin: false,
            roles: ['GUEST']
        },
        "/blogs": {
            templateUrl: 'app/components/blog/listblog.html',
            controller: 'BlogController',
            controllerAs: 'blogCtrl',
            requireLogin: true,
            roles: ['USER', 'ADMIN']
        },

    };

myApp.config(['$locationProvider', '$routeProvider', '$httpProvider', function ($locationProvider, $routeProvider, $httpProvider) {


    // allows the cookie with session id to be send back
    //$httpProvider.defaults.withCredentials = true;

    // fill up the path in the $routeProvider the objects created before
    for (var path in window.routes) {
        $routeProvider.when(path, window.routes[path]);
    }

    $routeProvider.otherwise({
        redirectTo: '/login'
    })

    $locationProvider.hashPrefix('!');

}]);


myApp.run(function ($rootScope, $location, AuthenticationFactory) {

   // $rootScope.$on('LOAD', function(){
     //   $rootScope.loading = true;
    //});

   // $rootScope.$on('UNLOAD', function(){
     //   $rootScope.loading = false;
    //});

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        //$scope.$emit('LOAD');
        // iterate through all the routes
        for (var i in window.routes) {
            // if routes is present make sure the user is authenticated 
            // before login using the authentication service            
            if (next.indexOf(i) != -1) {
                // if trying to access page which requires login and is not logged in 
                $rootScope.user = AuthenticationFactory.loadUserFromCookie();
                console.log($rootScope.user);
                $rootScope.authenticated = AuthenticationFactory.getUserIsAuthenticated();
                console.log($rootScope.authenticated);

                if (window.routes[i].requireLogin && !AuthenticationFactory.getUserIsAuthenticated()) {
                    $location.path('/login');
                }
                else if ((AuthenticationFactory.getUserIsAuthenticated())
                    &&
                    (window.routes[i].roles.indexOf(AuthenticationFactory.getRole()) == -1)) {
                    $location.path('/error');
                }
            }
        }
        //$scope.$emit('UNLOAD');
    });

    $rootScope.logout = function () {
        console.log($rootScope.userId);
        AuthenticationFactory.logout($rootScope.userId)
            .then(
            function (user) {
                AuthenticationFactory.setUserIsAuthenticated(false);
                AuthenticationFactory.setRole('GUEST');
                $rootScope.authenticated = false;
                $rootScope.isAdmin = false;
                $rootScope.isUser = false;
                $rootScope.islogin = false;
                $location.path('/login');
                console.log(user);
            },
            function (errorResponse) {

                console.log(errorResponse);
            }
            )
    }

     $rootScope.getSingleUser = function (userId) {
         debugger;
        console.log(userId);
        UserFactory.getUser(userId)
            .then(
            function (d) {
                $rootScope.customer = d;
                console.log(self.customer);
                $location.path('/viewUser');
            },
            function (errResponse) {
                console.error('Error while updating User');
            }
            );
    }


});
