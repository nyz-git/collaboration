var ForumCommentModule = angular.module('ForumCommentModule', []);

ForumCommentModule.factory('ForumCommentFactory', ['$http', '$q', function ($http, $q) {

    var forumurl = 'http://localhost:8080/Phoenix//forum/';

    var url = "http://localhost:8080/Phoenix//forumComment/"

    var factory = {
        getforumComments: getforumComments,
        createforumComment: createforumComment,
        approveForumRequest: approveForumRequest,
        disapproveForumRequest: disapproveForumRequest
    };

    return factory;

    //all forum comments by forum id
    function getforumComments(forumId) {
        var deferred = $q.defer();
        $http.get(url + 'get/' + forumId).
            then(function (response) {
                deferred.resolve(response.data);
                console.log('fetched forums comments');
            }, function (errResponse) {
                deferred.reject(errResponse);
                console.error('error fetching forumcomments');
            });
        return deferred.promise;
    }

    //create forum comment
    function createforumComment(forumComment, forumId) {
        var deferred = $q.defer();
        $http.post(url + 'create/'+forumId, forumComment).
            then(function (response) {
                deferred.resolve(response.data);
                console.log('created forum comment');
            }, function (errResponse) {
                deferred.reject(errResponse);
                console.error('error creating forumcomments');
            });
        return deferred.promise;
    }

        //approve forum request
    function approveForumRequest(request) {
        var deferred = $q.defer();
        $http.put(forumurl + 'approveMember', request).
            then(function (response) {
                deferred.resolve(response.data);
                console.log('approved forum request');
            }, function (errResponse) {
                deferred.reject(errResponse);
                console.error('error approve forum request');
            });
        return deferred.promise;
    }

    //disapprove forum request
    function disapproveForumRequest(request) {
        var deferred = $q.defer();
        $http.put(forumurl + 'disapproveMember', request).
            then(function (response) {
                deferred.resolve(response.data);
                console.log('disapproved forum request');
            }, function (errResponse) {
                deferred.reject(errResponse);
                console.error('error disapprove forum request');
            });
        return deferred.promise;
    }

}]);