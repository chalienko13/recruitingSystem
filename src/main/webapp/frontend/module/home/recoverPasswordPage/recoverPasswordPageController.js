/**
 * Created by IO on 06.05.2016.
 */

'use strict';

function recoverPasswordPageController($scope, $http, $routeParams) {

    $scope.passwordChange = false;
    $scope.username = '';
    $scope.userExist = false;
    $scope.errorMessage = '';
    $scope.t =  $routeParams.token;
    $scope.init = function () {
        $http({
            method: 'GET',
            url: '/recoverPassword/' + '?token=' +$scope.t,
            contentType: 'application/json'
        });
        $scope.email = $routeParams.email;
        console.log($scope.passwordSecond);

    };

    $scope.changePassword = function () {
        console.log($scope.passwordSecond);

        $http({
            method: 'POST',
            url: '/changePassword',
            contentType: 'application/json',
            data: {password: $scope.passwordSecond}
        }).success(function (data, status) {
            console.log(data);
            console.log(status);
            $scope.passwordChange = true;
            $scope.username = data.firstName;
            $scope.userExist = true;
        }).error(function (data, status, headers) {
            if (status == 409){
                $scope.userExist = this;
                $scope.message = data.message;
            }
        });
    }
}

angular.module('appRecoverPassword')
    .controller('recoverPasswordPageController', ['$scope', '$http', '$routeParams', recoverPasswordPageController]);