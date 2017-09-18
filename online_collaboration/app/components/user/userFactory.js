var UserModule = angular.module('UserModule', ['UploadModule']);

UserModule.factory('UserFactory', ['$http', '$q', function ($http, $q) {
    var url = 'http://localhost:8080/Phoenix/';

    return {
        getUser: getUser,
        updateUser: updateUser,
        addFriend: addFriend,
        getMyFriends: getMyFriends,
        getRequests: getRequests,
        acceptFriendRequest: acceptFriendRequest,
        rejectFriendRequest: rejectFriendRequest,
        getLatestUsers: getLatestUsers,
        fetchLatestBlogs: fetchLatestBlogs,
        fetchLatestForums: fetchLatestForums,
        fetchLatestFriends: fetchLatestFriends,
        getOnlineFriends: getOnlineFriends
    };

    function updateUser(user, userId) {
        var deferred = $q.defer();
        $http.put(url + '/user/get/' + userId, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
                console.log(response.data);
            },
            function (errResponse) {
                console.error('Error while updating User');
                deferred.reject(errResponse);
            }
            );
        return deferred.promise;
    }

    function getUser(userId) {
        var deferred = $q.defer();
        $http.get(url + '/user/get/' + userId)
            .then(
            function (response) {
                deferred.resolve(response.data);
                console.log(response.data);
            },
            function (errResponse) {
                console.error('Error while updating User');
                deferred.reject(errResponse);
            }
            );
        return deferred.promise;
    }

    function addFriend(request) {
        console.log(request);

        var deferred = $q.defer();
        $http.post(url + '/addFriend', request)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error('Error while sending request');
                deferred.reject(errResponse);
            }
            );
        return deferred.promise;
    }

    function getMyFriends(userId) {
        console.log(userId);

        var deferred = $q.defer();
        $http.get(url + '/friendList/' + userId)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error('Error while fetching friends');
                deferred.reject(errResponse);
            }
            );
        return deferred.promise;

    }

    function getOnlineFriends(userId) {
        console.log(userId);

        var deferred = $q.defer();
        $http.get(url + '/online/' + userId)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error('Error while fetching friends');
                deferred.reject(errResponse);
            }
            );
        return deferred.promise;

    }

    function getRequests(userId) {
        console.log(userId);

        var deferred = $q.defer();
        $http.get(url + '/friendRequests/' + userId)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error('Error while fetching friends');
                deferred.reject(errResponse);
            }
            );
        return deferred.promise;

    }

    function acceptFriendRequest(request) {
        debugger;
        console.log(request);

        var deferred = $q.defer();
        $http.put(url + '/acceptRequest', request)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error('Error while accepting request');
                deferred.reject(errResponse);
            }
            );
        return deferred.promise;

    }

    function rejectFriendRequest(request) {
        debugger;
        console.log(request);

        var deferred = $q.defer();
        $http.put(url + '/rejectRequest' , request)
            .then(
            function (response) {
                console.log(response.data);
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error('Error while rejecting request');
                deferred.reject(errResponse);
                console.log(errResponse);
            }
            );
        return deferred.promise;

    }

    function getLatestUsers() {
        var deferred = $q.defer();
        $http.get(url + '/latestusers').then(function (response) {
            deferred.resolve(response.data);
        }, function (errResponse) {
            deferred.reject(errResponse);
        });
        return deferred.promise;
    }

    function fetchLatestBlogs() {
		var deferred = $q.defer();

		$http.get(url + '/latestBlogs')
			.then(function (response) {
				console.log(response);
				deferred.resolve(response.data);
			}, function (errResponse) {
				console.error('Error while fetching blogs!');
				deferred.reject(errResponse);
			});

		return deferred.promise;
	}

    function fetchLatestForums(userId) {

        var deferred = $q.defer();

        $http.get(url + '/latestForums/' + userId).
            then(function (response) {
                console.log(response.data);
                deferred.resolve(response.data);
            }, function (errResponse) {
                console.error('error fetching forums');
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }

    function fetchLatestFriends() {
		var deferred = $q.defer();

		$http.get(url + '/latestFriends')
			.then(function (response) {
				console.log(response);
				deferred.resolve(response.data);
			}, function (errResponse) {
				console.error('Error while fetching friends!');
				deferred.reject(errResponse);
			});

		return deferred.promise;
	}

}]);
