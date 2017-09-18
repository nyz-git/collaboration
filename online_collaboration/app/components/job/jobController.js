
app.controller('jobController', ['jobFactory', '$http', '$scope', function (jobFactory, $http, $scope) {

	var self = this;
	self.jobs = [];
	self.job = { jobId: '', jobProfile: '', description: '', qualification: '', status:'' };

	self.submit = submit;
	self.edit = edit;
	self.remove = remove;
	self.reset = reset;

	fetchAlljobs();


	function fetchAlljobs() {
		jobFactory
			.fetchAlljobs()
			.then(function (d) {
				self.jobs = d;
			}, function (errResponse) {
				console.error('Error while fetching the jobs');
			})
	}

	function getjob(jobId) {
		jobFactory.getjob(jobId)
			.then(
			fetchAlljobs,
			function (d) {
				self.job = d;
			},
			function (errResponse) {
				console.error('error while fetching forum.')
			}
			);

	}


	function createjob(job) {
		jobFactory.createjob(job)
			.then(
			fetchAlljobs,
			function (d) {
				self.job = d;
			},
			function (errResponse) {
				console.error('Error while creating job');
			}
			);
	}

	function updatejob(job, jobId) {
		jobFactory.updatejob(job, jobId)
			.then(
			fetchAlljobs,
			function (d) {
				self.job = d;
			},
			function (errResponse) {
				console.error('Error while updating job');
			}
			);
	}

	function deletejob(jobId) {
		jobFactory.deletejob(jobId)
			.then(
			fetchAlljobs,
			function (d) {
				self.job = d;
			},
			function (errResponse) {
				console.error('Error while deleting job');
			}
			);
	}

	function submit() {
		if (self.job.jobId == '' || self.job.jobId == undefined) {
			console.log('Saving New job', self.job);
			createjob(self.job);
		} else {
			updatejob(self.job, self.job.jobId);
			console.log('job updated with id ', self.job.jobId);
		}
		reset();
	}

	function edit(jobId) {
		console.log('id to be edited', jobId);
		for (var i = 0; i < self.jobs.length; i++) {
			if (self.jobs[i].jobId === jobId) {
				self.job = angular.copy(self.jobs[i]);
				break;
			}
		}
	}

	function remove(jobId) {
		console.log('id to be deleted', jobId);
		if (self.job.jobId === jobId) {
			reset();
		}
		deletejob(jobId);
	}

	function reset() {
		self.job = { jobId: '', jobProfile: '', description: '', qualification: '', status:'' };
		$scope.jobForm.$setPristine(); //reset Form
	}

}]);
