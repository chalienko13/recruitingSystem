/**
 * Created by IO on 06.05.2016.
 */

function recoverRequestPageController($scope, $http) {
    $scope.emailSend = false;
    $scope.userExist = false;

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


        }).error(function (data, status, headers) {
            if (status === 409) {
                $scope.userExist = true;
            }
        });
    }

}

angular.module('appRecoverRequestPage')
    .controller('recoverRequestPageController', ['$scope', '$http', recoverRequestPageController]);