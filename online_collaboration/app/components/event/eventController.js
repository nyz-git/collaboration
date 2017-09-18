app.controller('eventController', ['eventFactory', '$http', '$scope', function (eventFactory, $http, $scope) {

    var self = this;
    self.events = [];
    self.event = { eventId: '', eventProfile: '', description: '', qualification: '', status: '' };

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;

    fetchAllEvents();


    function fetchAllEvents() {
        eventFactory
            .fetchAllEvents()
            .then(function (d) {
                self.events = d;
            }, function (errResponse) {
                console.error('Error while fetching the events');
            })
    }


    function createEvent(event) {
        eventFactory.createEvent(event)
            .then(
            fetchAllEvents,
            function (d) {
                self.event = d;
            },
            function (errResponse) {
                console.error('Error while creating event');
            }
            );
    }

    function updateEvent(event, eventId) {
        eventFactory.updateEvent(event, eventId)
            .then(
            fetchAllEvents,
            function (d) {
                self.event = d;
            },
            function (errResponse) {
                console.error('Error while updating event');
            }
            );
    }

    function deleteEvent(eventId) {
        eventFactory.deleteEvent(eventId)
            .then(
            fetchAllEvents,
            function (d) {
                self.event = d;
            },
            function (errResponse) {
                console.error('Error while deleting event');
            }
            );
    }

    function submit() {
        if (self.event.eventId == '' || self.event.eventId == undefined) {
            console.log('Saving New event', self.event);
            createevent(self.event);
        } else {
            updateevent(self.event, self.event.eventId);
            console.log('event updated with id ', self.event.eventId);
        }
        reset();
    }

    function edit(eventId) {
        console.log('id to be edited', eventId);
        for (var i = 0; i < self.events.length; i++) {
            if (self.events[i].eventId === eventId) {
                self.event = angular.copy(self.events[i]);
                break;
            }
        }
    }

    function remove(eventId) {
        console.log('id to be deleted', eventId);
        if (self.event.eventId === eventId) {
            reset();
        }
        deleteevent(eventId);
    }

    function reset() {
        self.event = { eventId: '', eventProfile: '', description: '', qualification: '', status: '' };
        $scope.eventForm.$setPristine(); //reset Form
    }

}]);

