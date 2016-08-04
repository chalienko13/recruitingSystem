'use strict';

function authorizationController($scope, TokenStorage, $http, $rootScope, $location, $window) {

    // $scope.authsuccess = false;

    $scope.login = function () {

        if ($scope.password === undefined) {
            console.log("Auth error");
        } else {
            $http({
                method: 'POST',
                url: '/loginIn',
                contentType: 'application/json',
                data: {email: $scope.email, password: $scope.password}
            }).success(function (data, status, headers) {
                TokenStorage.clear();
                TokenStorage.store(headers('X-AUTH-TOKEN_LOGIN_PASSWORD'), 'X-AUTH-TOKEN_LOGIN_PASSWORD');
                $rootScope.username = data.username;
                $rootScope.id = data.id;
                $rootScope.role = data.role;
/*              $location.url(data.redirectURL);*/
                $scope.errorcredential = false;
                //toDo this is crutch_oriented
                window.location.replace("/frontend/index.html#" + data.redirectURL);
            }).error(function (data, status, headers) {
                console.log(data);
                if (status == 401) {
                    $scope.errorcredential = true;
                }
            });
        }
    };

    $scope.facebookLogin = function () {
        FB.login(function (response) {
                if (response.authResponse) {
                    console.log('Welcome!  Fetching your information.... ');
                    FB.api('/me?fields=name,email', function (response) {
                        console.log(response);
                        console.log(response.email);
                        /*setting the user object*/
                        //$cookieStore.put('userObj', response);

                        /*get the access token*/
                        var FBAccessToken = FB.getAuthResponse();
                        var data = angular.extend(response, FBAccessToken);
                        console.log(FBAccessToken);
                        $http({
                            method: 'POST',
                            url: '/socialAuth/facebookAuth',
                            contentType: 'application/json',
                            data: {info: data}
                        }).success(function (data, status, headers) {
                            TokenStorage.store(headers('X-AUTH-TOKEN_SOCIAL'), 'X-AUTH-TOKEN_SOCIAL');
                            $rootScope.username = data.username;
                            $rootScope.id = data.id;
                            $rootScope.role = data.role;
                            $location.path(data.redirectURL);
                            $scope.errorcredential = false;
                        }).error(function (data, status, headers) {
                            console.log(data);
                            if (status == 401) {
                                $scope.errorcredential = true;
                            }
                        });
                        $scope.$apply();
                    });
                } else {
                    console.log('User cancelled login or did not fully authorize.');
                }
            },
            {scope: 'public_profile, email, user_birthday, user_location'}
        );
    };

    $scope.vkLogin = function () {
        $http({
            method: 'GET',
            url: 'https://oauth.vk.com/authorize',
            params: {
                client_id: 5484909,
                redirect_uri: 'http://localhost:8082/',
                display: 'page',
                response_type: 'token',
                v: '5.52'
            }
        }).success(function (data, status, headers) {
            console.log(data);
            console.log(status);
            console.log(headers);
        })
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