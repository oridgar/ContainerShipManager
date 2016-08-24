//Module Routes
//-------------
angular.module('csm').config(['$routeProvider', 
	function ($routeProvider) {
	$routeProvider
		.when('/servers', {
			controller: 'MainController',
			templateUrl: 'views/Servers.html'
		})
		.when('/containers', {
			controller: 'MainController',
			templateUrl: 'views/Containers.html'
		})
		.when('/users', {
			controller: 'MainController',
			templateUrl: 'views/Users.html'
		})
		.when('/login', {
			controller: 'MainController',
			templateUrl: 'login.html'
		})
		.when('/', {
			controller: 'MainController',
			templateUrl: 'views/Main.html'
		})
		
		.otherwise({ 
			redirectTo: '/' 
		});
}]);