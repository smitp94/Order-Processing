var myApp = angular.module("myApp", ['ngRoute']);
 
myApp.config(function($routeProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: 'login.html',
            controller: 'loginController'
        })
        .when('/showCustomers', {
            templateUrl: 'views/showCustomers.html',
            controller: 'customerController'
        })
        .when('/addCustomer', {
            templateUrl: 'views/addCustomer.html',
            controller: 'addCustomerController'
        })
        .when('/deleteCustomer/:customerId', {
            templateUrl: 'views/deleteCustomer.html',
            controller: 'deleteCustomerController'
        })
        /*.when('/deleteCustomer/:customer', {
            templateUrl: 'views/deleteCustomer.html',
            controller: 'deleteCustomerController'
        })*/
        .when('/editCustomer/:customerId', {
            templateUrl: 'views/editCustomer.html',
            controller: 'editCustomerController'
        })
       /* .when('/updateCustomer', {
            templateUrl: 'views/showUpdateCustomer.html',
            controller: 'postEditCustomerController'
        })*/
        .otherwise({
            redirectTo: '/login'
        });
});

myApp.controller('loginController',['$scope','$http', function($scope,$http) {
	console.log('In loginController');	
	$scope.submitLogin = function(isValid){
		console.log(isValid);
		if(isValid){
			$http.get('api/rest/verify/'+$scope.email+'/'+$scope.password)
			.success(function(data){
				 console.log("success");				 
				 location.href = "http://localhost:9090/SpringAngularOrderProcessingApp/#/showCustomers"
			}).error(function(status, data) {
				  console.log("Error...");
				 });
		}
	}
	
}]);

myApp.controller('customerController',['$scope','$http',function($scope,$http){
	function _getCustomersData() { 		
		
		console.log('In getCustomerData() method');
		$http.get('api/rest/all')
			.success(function(data){
				 //console.log('In success function of customer controller');
				  $scope.customers = data;
				 // _convertDate();
				  //console.log($scope.customers[0].customerId);
			}).error(function(status, data) {
				  console.log("Error...");
				 });
		
		/*$scope.go = function ( path ) {
			  $location.path( path );
			};*/
	}
	
	/*function _convertDate(){
		for(var i=0;i<$scope.customers.length;i++){
			var u=$scope.customers[i];
			var d= new Date(u.birthdate);
			$scope.customers[i].birthdate=d.toUTCString();
		}
	}*/
	
	_getCustomersData();
	
	
}]);


myApp.controller('addCustomerController',['$scope','$http', function($scope,$http) {
	console.log('In addCustomerController');	
	$scope.message='';
	$scope.customerForm={};
	$scope.addCustomer = function(){
		$http({ 
			method : 'POST', 
			url : 'api/rest/new', 
			data : angular.toJson($scope.customerForm), 
			headers : { 
				'Content-Type' : 'application/json' 
				} 
		}).then( _success, _error ); 
	}
	
	function _success(response) {	
		$scope.message="Customer Registration Successfull!!"
		_clearFormData() 
	} 

	function _error(response) { 
		$scope.message="Sorry! Customer Registration Failed!!"
		console.log(response.statusText); 
	} 
	
	//Clear the form 
	function _clearFormData() { 
		$scope.customerForm.customerId = 0; 
		$scope.customerForm.customerName = ""; 
		$scope.customerForm.birthdate = "";
		$scope.customerForm.mobile="";
		$scope.customerForm.email="";
		$scope.customerForm.password = ""; 
		$scope.customerForm.salesRep = 0; 
		}; 

	
}]);


myApp.controller('deleteCustomerController',['$scope','$http','$routeParams', function($scope,$http,$routeParams) {
	console.log('In deleteCustomerController');	
	$scope.custId=$routeParams.customerId;
	//$scope.customer=$routeParams.customer;
	
	console.log($scope.custId);
	
	
	
	_deleteCustomer();
	
	function _deleteCustomer() { 
		$http({ 
			method : 'DELETE', 
			url : 'api/rest/delete/' + $scope.custId 
			//url : 'api/rest/delete/' + $scope.customer.customerId
	}).then(_success, _error); 
	}; 
		
	
	function _success(response) {	
		$scope.message="Customer record deleted";
		console.log(response);
		
	} 

	function _error(response) { 
		$scope.message="Unable to delete customer record";
		console.log(response.statusText); 
	} 
	
	
	
}]);

myApp.controller('editCustomerController',['$scope','$http','$routeParams', function($scope,$http,$routeParams) {
	console.log('In editCustomerController');	
	$scope.custId=$routeParams.customerId;	
	
	console.log($scope.custId);
	
	_readCustomer();
	
	function _readCustomer(){
		$http.get('api/rest/read/'+$scope.custId)
		.success(function(data){
		 console.log("success: read customer");
		 $scope.customerForm=data;		 
		}).error(function(status, data) {
		  console.log("Error...");
		 });
	 }
	
	
	$scope.updateCustomer=function() {
		
		$http({ 
			method : 'PUT', 
			url : 'api/rest/update', 
			data : angular.toJson($scope.customerForm), 
			headers : { 
				'Content-Type' : 'application/json' 
				} 
		}).then( _success, _error ); 
	}; 
		
	
	function _success(response) {	
		$scope.message="Customer record successfully updated!";
		console.log(response);
		
	} 

	function _error(response) { 
		$scope.message="Unable to update customer record!";
		console.log(response.statusText); 
	} 
	
	
	
}]);


/*
myApp.controller('addPersonController',['$scope','$http', function($scope,$http) {
	
	$scope.personForm={
			firstName:"",
			lastName:"",
			email:"",
			mobile:0			
	};
	
	$scope.submitPerson = function() { 
		var method = 'POST'; 
		var url = 'addPersonServlet'; 
		
		$http({ 
			method : method, 
			url : url, 
			data : angular.toJson($scope.personForm), 
			headers : { 
				'Content-Type' : 'application/json' 
				} 
		}).then(function(response) {
            console.log(response.data);
            $scope.message = "Person record added to the database";
        }, function(response) {
            //fail case
            console.log(response);
            $scope.message = response;
        });

	  }; 		
	
}]);



myApp.controller('viewPersonController',['$scope','$http', function($scope,$http) {
	$scope.persons=[];
	
	$scope.personForm={
			firstName:"",
			lastName:"",
			email:"",
			mobile:0			
	};
	
	
	
	$scope.getAllPersonsFromServer= function() {	
		
		$http.get("http://localhost:8080/AngularJSHttpPost/getAllPersons")
			.success(function(data){
				  $scope.persons = data;				  
			}).error(function(status, data) {
				  console.log("Error...");
				 });
	};	
	
	function _getAllPersons(){
		$http.get("http://localhost:9090/AngularJSHttpPost/getAllPersons")
		.success(function(data){
			  $scope.persons = data;				  
		}).error(function(status, data) {
			  console.log("Error...");
			 });
	}
	
	_getAllPersons();
	
}]);
*/