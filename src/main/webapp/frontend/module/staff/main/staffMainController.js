/**
 * Created by Vova on 02.05.2016.
 */

function staffMainController($scope, staffMainService, $rootScope) {
    staffMainService.loadRecruitmentData().then(function success(data) {
        $scope.reqruitment = data.data;
        $scope.endDayRegistration = new Date(parseInt($scope.reqruitment.registrationDeadline)).toDateString();
        $scope.scheduleDeadline = new Date(parseInt( $scope.reqruitment.scheduleChoicesDeadline)).toDateString();
        $rootScope.username;
    }, function error() {
        console.log("error");
    });
}

angular.module('appStaffMain')
    .controller('staffMainController', ['$scope','staffMainService', '$rootScope', staffMainController]);