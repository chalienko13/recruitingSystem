/**
 * Created by dima on 30.04.16.
 */

function registrationController($scope, $http, registrationService) {
    $scope.registerStudent = function () {
        $scope.emailSend = false;
        $http({
            method: 'GET',
            url: '/registrationStudent/domainVerify',
            params: {email: $scope.email}
        }).success(function (data) {
            console.log(data);
            if (data == false) {
                $scope.serverNotExist = true;
                console.log("Verify Error");
            } else {
                $http({
                    method: 'POST',
                    url: '/registrationStudent',
                    contentType: 'application/json',
                    data: {
                        firstName: $scope.firstName,
                        secondName: $scope.secondName,
                        lastName: $scope.lastName,
                        email: $scope.email,
                        password: $scope.password
                    }
                }).success(function (data, status) {
                    if (status === 200) {
                        $scope.emailSend = true;
                        $scope.username = data.firstName;
                        $scope.email = data.email;
                        $scope.userExist = false;
                    }
                }).error(function (data, status) {
                    if (status === 409) {
                        $scope.userExist = true;
                    }
                });
            }
        });
    }
}

angular.module('appRegistration')
    .controller('registrationController', ['$scope', '$http', 'registrationService', registrationController]);