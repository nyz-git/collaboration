UploadModule.controller('UploadController', ['UploadFactory', 'AuthenticationFactory', '$rootScope', '$scope', '$timeout', function (UploadFactory, AuthenticationService, $rootScope, $scope, $timeout) {
    var self = this;
    self.picture = undefined;

    self.customer = AuthenticationFactory.loadUserFromCookie();

    // the decached technique is used to see the updated image immediately with out page refresh
    self.customer.pictureId = self.customer.pictureId + '?decached=' + Math.random();

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
        UploadService.uploadFile(self.picture)
            .then(
            function (response) {
                $rootScope.message = 'Profile picture updated successfully!';
                //message contains the pictureId updated in the backend too
                self.customer.pictureId = response.message + '?decached=' + Math.random();
                // update the controller user too
                $rootScope.user.pictureId = response.message + '?decached=' + Math.random();
                // need to update the cookie value too
                AuthenticationFactory.saveUser($rootScope.user);

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
}]);
