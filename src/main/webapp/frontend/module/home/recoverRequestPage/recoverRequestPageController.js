/**
 * Created by IO on 06.05.2016.
 */

function recoverRequestPageController($scope, $http, $timeout) {
    $scope.emailSend = false;
    $scope.userExist = false;
    $scope.showSuccessRequestRecovery = false;

    $scope.hideSuccessRequestRecoveryMessage = function() {
        $timeout(function() {
            $scope.showSuccessRequestRecovery = false;
        }, 3000);
    };

    $scope.sendEmail = function () {
        console.log($scope.email);
        $http({
            method: 'POST',
            url: '/recoverPassword',
            contentType: 'application/json',
            data: {email: $scope.email}
        }).success(function (data, status) {
            $scope.emailSend = true;
            $scope.email = data.email;
            $scope.username = data.firstName;
            $scope.userExist = false;
            $scope.successRequestRecoveryMessage = "Dear "+ $scope.username +", password has benn changed.";
            $scope.showSuccessRequestRecovery = true;
            $scope.hideSuccessRequestRecoveryMessage();
        }).error(function (data, status, headers) {
            if (status === 409) {
                $scope.userExist = true;
            }
        });
    };

}

angular.module('appRecoverRequestPage')
    .controller('recoverRequestPageController', ['$scope', '$http', '$timeout', recoverRequestPageController]);