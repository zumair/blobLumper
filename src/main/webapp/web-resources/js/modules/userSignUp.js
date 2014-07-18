var userSignUpModule = angular.module('userSignUpModule',[]);


userSignUpModule.controller('signUpController', ['$scope', function($scope) {
	$scope.EMAIL_REGULAR_EXP = /^[a-z0-9!#$%&'*+/=?^_`{|}~.-]+@[a-z0-9-]+(\.[a-z0-9-]+)*$/i;
	
	$scope.submitForm = function(isValid){
		
		
		
	};
	
	$scope.takeMeToLoginPage = function(){
		alert('I am in takeMeToLoginPage');
	};
	
	
	

}]);


userSignUpModule.directive('checkemailalreadyexists', function() {
	
	return {
		require: 'ngModel',
		restrict: 'A',
		controller: ['$scope', '$http', function($scope, $http ) {
			
				$scope.isUserEmailExists = function(email,ctrl) {
					
					$http({
							method: "post",
							url:    "/userService/isUserEmailExists",
							data: {
								value : email
							}
						})
					.success(function(data,ngModel) {
							var valid = false;
							if(data.status == "success"){
								valid = true;
							}
							ctrl.$setValidity('checkemailalreadyexists', valid);
					});
					
					
				
				}
		}],
		link: function(scope, elem, attr,ctrl,ngModel){
			
			
			elem.bind('blur', function() {
				//alert('In change and blur event');
				var elementValue = elem.val();
				scope.isUserEmailExists(elementValue,ctrl);
				
		    });
			
		}
		
	};
});




