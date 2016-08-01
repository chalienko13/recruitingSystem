/**
 * Created by Vova on 03.05.2016.
 */
function studentMenuController($scope, $location) {
    
    $scope.appform = function () {
        $location.path("/student/appform");
    };
    $scope.feedback = function () {
        $location.path("/student/feedback");
    };
    $scope.scheduling = function () {
        $location.path("/student/scheduling");
    };
    $scope.settings = function () {
        $location.path("/student/settings");
    };
}

angular.module('appStudentMenu')
    .controller('studentMenuController', ['$scope', '$location', studentMenuController]);