/**
 * Created by dima on 30.04.16.
 */

'use strict';

function headerController($scope, $location,$rootScope, TokenStorage) {
    
    $scope.loginPage = function () {
        $location.path("/authorization");
    };

    $scope.logOut = function () {
        $rootScope.authenticated = false;
        TokenStorage.clear();
/*        $location.path('/home');*/
        //toDo this is crutch_oriented
        window.location.replace("/");
    };
}

angular.module('appHeader')
    .controller('headerController', ['$scope', '$location','$rootScope', 'TokenStorage', headerController]);


