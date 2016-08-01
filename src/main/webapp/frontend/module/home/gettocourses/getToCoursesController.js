/**
 * Created by Vova on 02.05.2016.
 */
'use strict';
function getToCoursesController($scope,$http, $rootScope, $location) {
    $scope.registration = function () {
        console.log("registration");
        $location.path('/registration');
    };

}

angular.module('appGetToCourses')
    .controller('getToCoursesController', ['$scope', '$http', '$rootScope', '$location', getToCoursesController]);