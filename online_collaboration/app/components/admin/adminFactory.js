var AdminModule = angular.module('AdminModule', []);

AdminModule.factory('adminFactory', ['$http', '$q', function ($http, $q) {
    var url = 'http://localhost:8080/Phoenix//admin/';

    return {
        getUnapprovedBlogs: getUnapprovedBlogs,
        approveBlog: approveBlog,
        disapproveBlog: disapproveBlog,
        getUnapprovedUsers: getUnapprovedUsers,
        approveUser: approveUser,
        disapproveUser: disapproveUser,
        getApprovedUsers: getApprovedUsers,
        blockUser: blockUser
    };

    function getUnapprovedBlogs() {
        var deferred = $q.defer();
        $http.get(url + 'blogs').then(function (response) {
            deferred.resolve(response.data);
        }, function (errResponse) {
            deferred.reject(errResponse);
        });
        return deferred.promise;
    }

    function approveBlog(blogId) {
        var deferred = $q.defer();
        $http.put(url + 'approveBlog/' + blogId).then(function (response) {
            deferred.resolve(response.data);
        }, function (errResponse) {
            deferred.reject(errResponse);
        });
        return deferred.promise;
    }

    function disapproveBlog(blogId) {
        var deferred = $q.defer();
        $http.put(url + 'disapproveBlog/' + blogId).then(function (response) {
            deferred.resolve(response.data);
        }, function (errResponse) {
            deferred.reject(errResponse);
        });
        return deferred.promise;
    } 

    function getUnapprovedUsers() {
        var deferred = $q.defer();
        $http.get(url + 'users').then(function (response) {
            deferred.resolve(response.data);
        }, function (errResponse) {
            deferred.reject(errResponse);
        });
        return deferred.promise;
    }

    function getApprovedUsers() {
        var deferred = $q.defer();
        $http.get(url + 'approvedusers').then(function (response) {
            deferred.resolve(response.data);
        }, function (errResponse) {
            deferred.reject(errResponse);
        });
        return deferred.promise;
    }

    function approveUser(userId) {
        var deferred = $q.defer();
        $http.put(url + 'approveUser/' + userId).then(function (response) {
            deferred.resolve(response.data);
        }, function (errResponse) {
            deferred.reject(errResponse);
        });
        return deferred.promise;
    }

    function disapproveUser(userId) {
        var deferred = $q.defer();
        $http.put(url + 'disapproveUser/' + userId).then(function (response) {
            deferred.resolve(response.data);
        }, function (errResponse) {
            deferred.reject(errResponse);
        });
        return deferred.promise;
    } 

    function blockUser(userId) {
        var deferred = $q.defer();
        $http.put(url + 'blockUser/' + userId).then(function (response) {
            deferred.resolve(response.data);
        }, function (errResponse) {
            deferred.reject(errResponse);
        });
        return deferred.promise;
    }	

}]);