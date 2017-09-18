app.factory('eventFactory', ['$http', '$q', function ($http, $q) {
    
    var url = 'http://localhost:8080/Phoenix//event/';

    return {
        fetchAllEvents: fetchAllEvents,
        createEvent: createEvent,
        updateEvent: updateEvent,
        deleteEvent: deleteEvent
    };

    // getting all events
    function fetchAllEvents() {
        var deferred = $q.defer();
        $http.get(url + 'list').
            then(function (response) {
                console.log(response.data);
                deferred.resolve(response.data);
            }, function (errResponse) {
                deferred.reject(errResponse);
                console.error('error fetching events');
            });
        return deferred.promise;
    }

    // create event 
    function createEvent(event) {
        var deferred = $q.defer();
        debugger;
        $http.post(url + 'create', event).
            then(function (response) {
                console.log(response.data);
                deferred.resolve(response.data);
            }, function (errResponse) {
                deferred.reject(errResponse);
                console.error('error create event');
            });
        return deferred.promise;
    }

    // update event
    function updateEvent(eventId, event) {
        var deferred = $q.defer();
        $http.put(url + 'get/' + eventId, event).
            then(function (response) {
                console.log(response.data);
                deferred.resolve(response);
            }, function (errResponse) {
                deferred.reject(errResponse);
                console.error('error update event');
            });
        return deferred.promise;
    }

    // delete event
    function deleteEvent(eventId) {
        var deferred = $q.defer();
        $http.put(url + 'delete/' + eventId).
            then(function (response) {
                console.log(response.data);
                deferred.resolve(response);
            }, function (errResponse) {
                deferred.reject(errResponse);
                console.error('error delete event');
            });
        return deferred.promise;
    }
}])