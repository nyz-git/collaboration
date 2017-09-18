var ChatModule = angular.module("ChatModule", []);

ChatModule.service("ChatService", ['$q', '$rootScope', '$timeout', function ($q, $rootScope, $timeout) {
    var service = {}, listener = $q.defer(), socket = {
        client: null,
        stomp: null
    }, messageIds = [];

    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "http://localhost:8080/Phoenix/chat";
    service.CHAT_TOPIC = "/topic/message";
    service.CHAT_BROKER = "/app/chat";

    service.receive = function () {
        //The only thing this function does is returning the deferred used to send messages at.
        return listener.promise;
    };

    service.send = function (message) {
        //sends the message as a JSON object
        var id = Math.floor(Math.random() * 1000000);
        //generates id
        socket.stomp.send(service.CHAT_BROKER, {
            priority: 9
        }, JSON.stringify({
            username: $rootScope.user.username,
            message: message,
            id: id
        }));
        messageIds.push(id);
        //adds it to array of ids
    };

    var reconnect = function () {
        //When the connection to the Websocket server is lost, it will call the reconnect() function which will attempt to initialize the connection again after 30 seconds.
        $timeout(function () {
            initialize();
        }, this.RECONNECT_TIMEOUT);
    };

    var getMessage = function (data) {
        //will translate the Websocket data body to the model required by the controller.
        var message = JSON.parse(data), out = {};
        out.username = message.username;
        out.message = message.message;
        out.time = new Date(message.time);
        //parse the JSON string to an object, and it will set the time as a Date object.
        if (_.includes(messageIds, message.id)) {
            out.self = true;
            //If the message ID is listed in the messageIds array, then it means the message originated from this client, so it will set the self property to true.
            messageIds = _.remove(messageIds, message.id);
        }
        return out;
    };

    var startListener = function () {
        //will listen to the /topic/message topic on which all messages will be received
        socket.stomp.subscribe(service.CHAT_TOPIC, function (data) {
            listener.notify(getMessage(data.body));
            //will then send the data to the deferred which will be used by the controllers
        });
    };

    var initialize = function () {
        //this is done for setting up the service.
        socket.client = new SockJS(service.SOCKET_URL);
        socket.stomp = Stomp.over(socket.client);
        socket.stomp.connect({}, startListener);
        socket.stomp.onclose = reconnect;
        //will set up the SockJS Websocket client and use it for the Stomp.js websocket client.
    };

    initialize();
    return service;

}]);