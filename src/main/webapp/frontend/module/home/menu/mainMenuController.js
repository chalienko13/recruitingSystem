/**
 * Created by dima on 30.04.16.
 */

'use strict';

function mainMenuController($scope, $location) {

    $scope.getClass = function (path) {
        return ($location.path().substr(0, path.length) === path) ? 'active' : '';
    };

    $scope.educationnc = function () {
        $location.path("/educationNC");
    };

    $scope.gettocoursesnc = function () {
        $location.path("/getToCourses");
    };

    $scope.home = function () {
        $location.path("/home");
    };
}

angular.module('appMenuMain')
    .controller('mainMenuController', ['$scope', '$location', mainMenuController]);