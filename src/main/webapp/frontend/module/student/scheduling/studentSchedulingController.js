/**
 * Created by IO on 02.05.2016.
 */


function studentSchedulingController($scope, ngToast, $http) {
	$http.get('../../student/schedule').success(function(data) {
		$scope.schedule = data;
		console.log($scope.schedule);
		if ($scope.schedule.message != null) {
			$scope.resultMessage = data;
		}
		console.log($scope.schedule);
	});

	$scope.update = function() {
		var jsonData = angular.toJson($scope.schedule.timePoints);
		var req= $http.post('../../student/updateSchedule', $scope.schedule.priorities);
		req.success(function(data) {
			$scope.resultMessage = data;
			var toastMessage = {
	                content: $scope.resultMessage.message,
	                timeout: 5000,  
	                horizontalPosition: 'center',
	                verticalPosition: 'bottom',
	                dismissOnClick: true,
	                combineDuplications: true,
	                maxNumber: 2
	            };
			if ($scope.resultMessage.type == 'ERROR') {
				var myToastMsg = ngToast.warning(toastMessage);
			}
			else if ($scope.resultMessage.type == 'SUCCESS') {
				var myToastMsg = ngToast.success(toastMessage);
			}
		});
	}
}

angular.module('studentScheduling')
    .controller('studentSchedulingController', ['$scope', 'ngToast', '$http', 'studentSchedulingService', studentSchedulingController]);