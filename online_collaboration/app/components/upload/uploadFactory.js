var UploadModule = angular.module('UploadModule', []);

UploadModule.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            console.log(model);
            var modelSetter = model.assign;
            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

// factory that contains the file upload function
UploadModule.factory('UploadFactory', ['$q', '$http', '$rootScope', function ($q, $http, $rootScope) {

    var url = 'http://localhost:8080/Phoenix/';

    return {
        uploadFile: uploadFile,
    };
    // uploadFile function to upload the image on the server
    function uploadFile(file) {

        var deferred = $q.defer();

        // NOTE: the 'Content-Type' is undefined to add a boundary between the multipart content
        // and other data content which is added automatically thats why here we don't use 

        var fd = new FormData();
        fd.append('file', file);
        // send the user id which can be used to update the usera
        // and to set the file name
        fd.append('id', $rootScope.user.userId);
        console.log(fd);
        $http.post(url + '/uploadPicture', fd, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        })
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (error) {
                console.log(error);
                deferred.reject(error);
            }
            );
        return deferred.promise;
    }
}]);