//Services
//--------
angular.module('csm').service('simpleService', ['$http', function ($http) {
	//Data members
    var urlBase = '/csm';

	//Methods
	/*
	this.getCustomers = function() {
		return customers;
	};
	
	this.addCustomer = function($customer) {
		customers.push($customer);
	};

	*/
	this.sayHello = function($scope) {
		$http.get('http://rest-service.guides.spring.io/greeting', { headers: { 'content-type' : 'application/json' }, params: {} })
			.success(function(data) {$scope.greeting = data; });
	};

	this.login = function($user,$password,alertid) {
		
		$http({
	        url: urlBase + '/login',
	        method: "POST",
	        headers: { 'Content-Type' : 'application/x-www-form-urlencoded' }, 
	        params: {username: $user, password: $password}
	        //data: 'username=' + $user + '&password=' + $password
	    }).success(function () {
	    	//TODO: Fix it!
	    	window.location.href = '/csm';
	    }).failed(function () {
	    	$('#' + alertid).show();
	    });
	    /*
		.then(function(response) {
            // success
    	}, 
    	function(response) { // optional
            // failed
	    });
		$http.post(urlBase + '/login', { 
			headers: { 'Content-Type' : 'application/x-www-form-urlencoded' }, 
		    params: {username: $user, password: $password}
		});
		*/
	};

	this.logout = function() {
		$http({
	        url: urlBase + '/logout',
	        method: "POST",
	        headers: { 'Content-Type' : 'application/x-www-form-urlencoded' }
	        //params: {username: $user, password: $password}
	        //data: 'username=' + $user + '&password=' + $password
	    }).success(function () {
	    	//TODO: Fix it!
	    	window.location.href = '/csm/login.html';
	    });

		//$http.post(urlBase + '/logout', { headers: { 'content-type' : 'application/x-www-form-urlencoded' }, params: {} });
	};

	//Users

	this.getUsersList = function() {
		return $http({
	        url: urlBase + '/user',
	        method: "GET",
	        headers: { 'Content-Type' : 'application/json' }
	    }).then(function (response) {
			return response.data;
		});

	};

	this.createUser = function($userDetails) {

	};

	this.getUser = function($userId) {

	};

	this.deleteUser = function($userId) {

	};

	//System

	this.getServersList = function() {
		return $http({
	        url: urlBase + '/server',
	        method: "GET",
	        headers: { 'Content-Type' : 'application/json' }
	    }).then(function (response) {
			return response.data;
		});
	};

	this.createSystem = function(systemDetails) {
		return $http({
	        url: urlBase + '/server',
	        method: "POST",
	        headers: { 'Content-Type' : 'application/json;charset=UTF-8' },
	        data : systemDetails
	    }).then(function (response) {
			return response.data;
		});
	};

	this.getSystem = function($systemId) {

	};

	this.sendSystemCommand = function($systemId, $command) {

	};

	this.deleteSystem = function(systemId) {
		return $http({
	        url: urlBase + '/server/' + systemId,
	        method: "DELETE",
	        headers: { 'Content-Type' : 'application/json;charset=UTF-8' }
	    }).then(function (response) {
			return response.data;
		});
	};


	//Container
	this.getContainersList = function() {
		return $http({
	        url: urlBase + '/container',
	        method: "GET",
	        headers: { 'Content-Type' : 'application/json' }
	    }).then(function (response) {
			return response.data;
		});
	};

	this.createContainer = function(containerDetails) {
		return $http({
	        url: urlBase + '/container',
	        method: "POST",
	        headers: { 'Content-Type' : 'application/json;charset=UTF-8' },
	        data : containerDetails
	    }).then(function (response) {
			return response.data;
		});
	};

	this.getContainer = function(containerId) {
	};

	this.deleteContainer = function(containerId) {
		return $http({
	        url: urlBase + '/container/' + containerId,
	        method: "DELETE",
	        headers: { 'Content-Type' : 'application/json;charset=UTF-8' }
	    }).then(function (response) {
			return response.data;
		});
	};

	this.startContainer = function(containerId) {
		return $http({
	        url: urlBase + '/container/' + containerId,
	        method: "POST",
	        headers: { 'Content-Type' : 'application/json;charset=UTF-8' },
	        params: { "action" : "start"  }
	    }).then(function (response) {
			return response.data;
		});
	};

	this.stopContainer = function(containerId) {
		return $http({
	        url: urlBase + '/container/' + containerId,
	        method: "POST",
	        headers: { 'Content-Type' : 'application/json;charset=UTF-8' },
	        params: { "action" : "stop" }
	    }).then(function (response) {
			return response.data;
		});
	};

	this.restartContainer = function(containerId) {
		return $http({
	        url: urlBase + '/container/' + containerId,
	        method: "POST",
	        headers: { 'Content-Type' : 'application/json;charset=UTF-8' },
	        params: { "action" : "restart" }
	    }).then(function (response) {
			return response.data;
		});
	};

	this.getContainerStatus = function(containerId) {
		return $http({
	        url: urlBase + '/container/' + containerId,
	        method: "POST",
	        headers: { 'Content-Type' : 'application/json;charset=UTF-8' },
	        params: { "action" : "status" }
	    }).then(function (response) {
			return response.data;
		});
	};

	this.registerServer = function($serverDetails) {

	};

	this.unregisterServer = function($systemId) {

	};
}]);