//var demoApp = angular.module('demoApp');


//Controllers
//-----------

//Note that the parameters in the newer versions are different. you must pass array to the second parameter.
//In the array there should be two cells: the scope variable and a function.
angular.module('csm').controller('LoginController', ['$scope', '$http', 'simpleService', function ($scope, $http, simpleService) {
	
	$scope.login = function() {
		simpleService.login($scope.login.user,$scope.login.password);
	};
}]);