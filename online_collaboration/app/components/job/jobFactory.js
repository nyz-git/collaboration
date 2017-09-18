app.factory('jobFactory', ['$http', '$q', function ($http, $q) {
    
    var url = 'http://localhost:9090/Phoenix//job/'

    var factory = {
        fetchAlljobs: fetchAlljobs,
        createjob: createjob,
        updatejob: updatejob,
        deletejob: deletejob,
        getjob: getjob
    }

    return factory;

    // get all jobs
    function fetchAlljobs() {
        var deferred = $q.defer();

        $http.get(jobUrl + 'list')
            .then(function (response) {
                $log.info(response);
                deferred.resolve(response.data);
            }, function (errResponse) {
                console.error('Error while fetching jobs!');
                deferred.reject(errResponse);
            });

        return deferred.promise;
    }

    // get job by job id
    function getjob(jobId) {

        var deferred = $q.defer();
        
        $log.info($q);
        $http.get(address + 'get/' + jobId)
            .then(function (response) {
                $log.info(response);
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error('Error getting job');
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }


    //create job
    function createjob(job) {

        var deferred = $q.defer();

        $http.post(address + 'create', job)
            .then(
            function (response) {
                $log.info(response);
                deferred.resolve(response.data);
            },
            function (errResponse) {
                console.error('Error adding job');
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }


    //update job
    function updatejob(job, jobId) {
        var deferred = $q.defer();
        $http.put(address + 'get/' + jobId, job)
            .then(function (response) {
                $log.info(response);
                deferred.resolve(response.data);
            }, function (errResponse) {
                console.error('Error updating job');
                deferred.reject(errResponse);
            });

        return deferred.promise;
    }


    // delete job
    function deletejob(jobId) {
        var deferred = $q.defer();
        $http.delete(address + 'delete/' + jobId)
            .then(function (response) {
                $log.info(response);
                deferred.resolve(response.data);
            }, function (errResponse) {
                console.error('Error deleting job');
                deferred.reject(errResponse);
            });
        return deferred.promise;
    }
}])