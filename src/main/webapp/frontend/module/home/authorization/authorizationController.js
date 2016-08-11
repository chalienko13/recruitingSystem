'use strict';

function authorizationController($scope, $http, $rootScope, $location, $timeout) {
    $scope.showWrongCredentials = false;
    $scope.wrongCredentialsMessage = "Wrong Credentials !"

    $scope.hideWrongCredentialsMessage = function() {
        $timeout(function() {
            $scope.showWrongCredentials = false;
        }, 5000);
    };

    $scope.login = function () {

        if ($scope.password === undefined) {
            console.log("Auth error");
            $scope.showWrongCredentials = true;
            $scope.hideWrongCredentialsMessage();
        } else {
            $http({
                method: 'POST',
                url: '/j_spring_security_check',
                contentType: 'application/json',
                params: {username: $scope.email, password: $scope.password}
            }).success(function (data) {
                //toDo this is crutch_oriented
                $http({"method" : "GET",
                    url: '/authUrl'
                }).success(function(data){
                    window.location.replace("/frontend/index.html#" + data.redirectUrl);
                }).error(function (data, status) {
                    console.log("Error " + data);
                        $scope.showWrongCredentials = true;
                        $scope.hideWrongCredentialsMessage();
                });
            }).error(function (data, status, headers) {
                console.log("Error " + data);
                    $scope.showWrongCredentials = true;
                    $scope.hideWrongCredentialsMessage();

            });
        }
    };


    $scope.registration = function () {
        console.log("registation");
        $location.path('/registration');
    };


    $scope.recoverPassword = function () {
        console.log("recoverPassword");
        $location.path('/recoverPasswordRequest');
    }
}

angular.module('appAuthorization')
    .controller('authorizationController', ['$scope', '$http', '$rootScope', '$location', '$timeout', authorizationController]);