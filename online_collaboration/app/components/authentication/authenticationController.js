AuthenticationModule.controller('AuthenticationController', ['AuthenticationFactory', '$rootScope', '$location', '$timeout', function (AuthenticationFactory, $rootScope, $location, $timeout) {
    var self = this;
    self.credentials = {};
    self.error = false;
    self.authError = false;
    self.client = { id: undefined, firstName: '', lastName: '', username: '', emailId: '', password: '', confirmPassword: '', role: '', gender: '' };

    // once the controller loads call the jQuery
    $timeout(function () {
        load();
    }, 100);

    self.login = function () {
        AuthenticationFactory.login(self.credentials)
            .then(
            function (user) {
                if (user.enabled === 'FALSE') {
                    self.authError = true;
                    $rootScope.message = "Your account has been blocked."
                }else if(user.status === 'REJECT'){
                    self.authError = true;
                    $rootScope.message = "Your account has not been approved."
                } else if (user.status === 'PENDING') {
                    self.authError = true;
                    $rootScope.message = "Sorry! You are not been approved yet!.";
                } else if (user.userId == '' || user.userId == undefined || user.username == null) {
                    self.authError = true;
                    $rootScope.message = "Incorrect username or password."
                } else {
                    AuthenticationFactory.setUserIsAuthenticated(true);
                    AuthenticationFactory.setRole(user.role);
                    $rootScope.authenticated = true;
                    $rootScope.message = 'Welcome ' + user.firstName;
                    AuthenticationFactory.saveUser(user);

                    switch (user.role) {
                        case 'ADMIN':
                            $location.path('/user/home');
                            $rootScope.isAdmin = true;
                            break;
                        case 'USER':
                            $location.path('/user/home');
                            $rootScope.isUser = true;
                            break;
                        default:
                            $location.path('/error/403');
                    }
                    $rootScope.islogin = true;
                }
                console.log(user);
            },
            function (errorResponse) {
                console.log(errorResponse);
                AuthenticationFactory.setUserIsAuthenticated(false);
                $rootScope.authenticated = false;
                self.error = true;
            }
            )
    }


    self.register = function () {
        AuthenticationFactory.register(self.client)
            .then(
            function (user) {
                AuthenticationFactory.setUserIsAuthenticated(false);
                $rootScope.authenticated = false;
                self.register = true;
                $rootScope.msg = "Registration successful! You will get an email after approval.";
                $location.path('/login');
            },
            function (errorResponse) {
                console.log(errorResponse);
                AuthenticationFactory.setUserIsAuthenticated(false);
                $rootScope.authenticated = false;
                self.error = true;
            }
            )
    }

}]);