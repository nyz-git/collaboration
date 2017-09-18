var AuthenticationModule = angular.module('AuthenticationModule', ['ngCookies']);

AuthenticationModule.factory('AuthenticationFactory', ['$http', '$q', '$rootScope', '$cookies', function ($http, $q, $rootScope, $cookies) {
    var url = 'http://localhost:8080/Phoenix/';
    var userIsAuthenticated = false;
    var role = 'GUEST';

    return {
        setUserIsAuthenticated: setUserIsAuthenticated,
        getUserIsAuthenticated: getUserIsAuthenticated,
        loadUserFromCookie: loadUserFromCookie,
        saveUser: saveUser,
        setRole: setRole,
        getRole: getRole,
        login: login,
        register: register,
        logout: logout
    };

    function setUserIsAuthenticated(value) {
        userIsAuthenticated = value;
    }

    function getUserIsAuthenticated() {
        return userIsAuthenticated;
    }

    function setRole(value) {
        role = value;
    }

    function getRole() {
        return role;
    }

    function login(credentials) {
        console.log(credentials);

        var deferred = $q.defer();
        $http.post(url + 'login', credentials)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error('Error while logging in');
                $rootScope.errorMessage = "Invalid credentials."
                deferred.reject(errResponse);
            }
            );
        return deferred.promise;

    }

    function register(user) {
        console.log(user);

        var deferred = $q.defer();
        $http.post(url + '/register', user)
            .then(
            function (response) {
                deferred.resolve(response.data);
                $rootScope.successMessage = "Registration successful! You will get an email after approval.";
            },
            function (errResponse) {
                console.error('Error while registering');
                deferred.reject(errResponse);
            }
            );
        return deferred.promise;

    }

    function logout(userId) {
        var deferred = $q.defer();
        $http.put(url + '/logout/' + userId)
            .then(function (response) {
                $cookies.putObject('user', undefined);
                userIsAuthenticated = false;
                role = 'GUEST';
                deferred.resolve(response);
                console.log(response);
            },
            function (errResponse) {
                deferred.reject(errResponse);
                console.log(errResponse);
            });
        return deferred.promise;
    }

    function loadUserFromCookie() {
     
        user = $cookies.getObject('user');
        console.log(user)
        if (user) {
            userIsAuthenticated = true;
            $rootScope.authenticated = true;
            $rootScope.message = 'Welcome ' + user.firstName;
            $rootScope.firstName = user.firstName;
            $rootScope.lastName = user.lastName;
            $rootScope.emailId = user.emailId;
            $rootScope.gender = user.gender;
            $rootScope.userId = user.userId;
            console.log($rootScope.userId);
            role = user.role;
            console.log(role);
        }
        else {
            userIsAuthenticated = false;
            role = 'GUEST';
        }
        return user;
    }

    function saveUser(user) {
        // save the user inside the cookie
        $cookies.putObject('user', user);
        role = user.role;
        userIsAuthenticated = true;

    }

}]);