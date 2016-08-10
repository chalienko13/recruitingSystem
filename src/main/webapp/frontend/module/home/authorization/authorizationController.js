'use strict';

function authorizationController($scope, TokenStorage, $http, $rootScope, $location, $window) {

    $scope.login = function () {

        if ($scope.password === undefined) {
            console.log("Auth error");
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
                    if (status == 401) {
                        $scope.errorcredential = true;
                    }
                });
            }).error(function (data, status, headers) {
                console.log("Error " + data);
                if (status == 401) {
                    $scope.errorcredential = true;
                }
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
    .controller('authorizationController', ['$scope', 'TokenStorage', '$http', '$rootScope', '$location', authorizationController]);