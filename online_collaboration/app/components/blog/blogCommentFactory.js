var BlogCommentModule = angular.module('BlogCommentModule', []);

BlogCommentModule.factory('BlogCommentFactory', ['$http', '$q', function ($http, $q) {

    var url = "http://localhost:8080/Phoenix//blogComment/"

    var factory = {
        getBlogComments: getBlogComments,
        createBlogComment: createBlogComment,
        deleteBlogComment: deleteBlogComment
    };

    return factory;

    //all blog comments by blog id
    function getBlogComments(blogId) {
        var deferred = $q.defer();
        $http.get(url + 'get/' + blogId).
            then(function (response) {
                deferred.resolve(response.data);
                console.log('fetched blogs comments');
            }, function (errResponse) {
                deferred.reject(errResponse);
                console.error('error fetching blogcomments');
            });
            return deferred.promise;
    }

    //create blog comment
    function createBlogComment(blogComment, blogId) {
        var deferred = $q.defer();
        $http.post(url + 'create/' +blogId, blogComment).
            then(function (response) {
                deferred.resolve(response.data);
                console.log('created blog comment');
            }, function (errResponse) {
                deferred.reject(errResponse);
                console.error('error creating blogcomments');
            });
            return deferred.promise;
    }

    //delete blog comment
    function deleteBlogComment(blogCommentId) {
        var deferred = $q.defer();
        $http.delete(blogCommentUrl + 'delete/' + blogCommentId).
            then(function (response) {
                deferred.resolve(response.data);
                console.log('deleted blog comment');
            }, function (errResponse) {
                deferred.reject(errResponse);
                console.error('error deleting blogcomment');
            });
        return deferred.promise;
    }

}]);