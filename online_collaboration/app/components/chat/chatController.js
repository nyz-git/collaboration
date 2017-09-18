ChatModule.controller('ChatController', ['$scope','$rootScope', 'ChatService', function ($scope,$rootScope, ChatService) {
    

    $scope.messages = [];
    $scope.message = "";
    $scope.max = 140;

    this.addMessage = function () {
        console.log('hi');
        ChatService.send($scope.message);
        $scope.message = "";
    };

    ChatService.receive().then(null, null, function (message) {
        console.log(message);
        $scope.messages.push(message);
    });
}])