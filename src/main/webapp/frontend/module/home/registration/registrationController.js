/**
 * Created by dima on 30.04.16.
 */

function registrationController($scope, $http, $timeout, registrationService) {
    $scope.showSuccessRegistration = false;

    $scope.hideSuccessRegistrationMessage = function() {
        $timeout(function() {
            $scope.showSuccessRegistration = false;
        }, 3000);
    };

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
                        $scope.successRegistrationMessage = "Dear " + $scope.username + ", on your email - " + $scope.email + "address has been sent confirm token. Please, confirm youremail";
                        $scope.showSuccessRegistration = true;
                        $scope.hideSuccessRegistrationMessage();
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
    .controller('registrationController', ['$scope', '$http', '$timeout', 'registrationService', registrationController]);