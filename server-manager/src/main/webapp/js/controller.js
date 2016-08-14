//Controllers
//-----------

//Note that the parameters in the newer versions are different. you must pass array to the second parameter.
//In the array there should be two cells: the scope variable and a function.
angular.module('csm').controller('MainController', ['$scope', '$http', 'simpleService', function ($scope, $http, simpleService) {
	
	//$scope.friends = simpleService.getCustomers();
	
	/*	
	$scope.addCustomer = function() {
		simpleService.addCustomer({firstname: $scope.newCustomer.firstname, lastname: $scope.newCustomer.lastname});
	};
	
	*/
	$scope.sayHello = function() { 
		simpleService.sayHello($scope);
	};

	$scope.logout = function() {
		simpleService.logout();
	};

	$scope.getUsersList = function() {
		simpleService.getUsersList().then(function (usersList) {
			$scope.usersList = usersList;
		});
	};

	$scope.getServersList = function() {
		simpleService.getServersList().then(function (serversList) {
			$scope.serversList = serversList;
		});
	};

	$scope.getContainersList = function() {
		simpleService.getContainersList().then(function (containersList) {
			$scope.containersList = containersList;
		});
	};

	$scope.addServer = function() {
		simpleService.createSystem($scope.newserver).then(function () {
			$scope.init();
		});
	};

	$scope.deleteServer = function() {
		simpleService.deleteSystem($scope.deleteId).then(function () {
			$scope.init();
		});
	};


	$scope.addContainer = function() {
		simpleService.createContainer($scope.newcontainer).then(function () {
			$scope.init();
		});
	};

	$scope.deleteContainer = function() {
		simpleService.deleteContainer($scope.deleteId).then(function () {
			$scope.init();
		});
	};



	//Initialization
	//--------------

	$scope.init = function () {
		$scope.getUsersList();
		$scope.getServersList();
		$scope.getContainersList();
	};
	$scope.init();
}]);