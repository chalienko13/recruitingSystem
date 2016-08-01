/**
 * Created by dima on 30.04.16.
 */

function menuController($scope, $http, $location, $rootScope) {

    var roles;
    $http.get('/currentUser')
        .success(function (user) {
            roles = user.roles;
            console.log(user.roles);
            var userRoles;
            angular.forEach(roles,function (item, i) {
                userRoles +=item.roleName;
            });
            console.log(userRoles);

            $scope.isExist = false;

            if (userRoles.match("ROLE_ADMIN") && userRoles.match("ROLE_TECH")){
                $scope.isExist = true;
            }

            if(userRoles.match("ROLE_ADMIN") && userRoles.match("ROLE_SOFT")){
                $scope.isExist = true;
            }

            console.log($scope.isExist);
        });
    
    /*staff menu*/
    $scope.studentManagement = function () {
        $location.path("/admin/studentManagement");
    };

    $scope.scheduling = function () {
        $location.path("/admin/staffScheduling");
    };

    $scope.getClass = function (path) {
        return ($location.path().substr(0, path.length) === path) ? 'active' : '';
    };
    /*staff menu*/
    
    
    
    $scope.mainAdmin = function () {
        $location.path("/admin/main");
    };

    $scope.staffManAdmin = function () {
        $location.path("/admin/staffmanagement");
    };

    $scope.studManAdmin = function () {
        $location.path("/admin/studentmanagement");
    };

    $scope.shedulAdmin = function () {
        $location.path("/admin/scheduling");
    };

    $scope.reportsAdmin = function () {
        $location.path("/admin/report");
    };

    $scope.formSettingsAdmin = function () {
        $location.path("/admin/adminformsettings");
    };

    $scope.recruitmentSettingsAdmin = function () {
        $location.path("/admin/recruitment");
    };

    $scope.notificationSettingsAdmin = function () {
        $location.path("/admin/notification");
    };

    $scope.personalSettingsAdmin = function () {
        $location.path("/admin/personal");
    };
    
}

angular.module('appMenu')
    .controller('menuController', ['$scope', '$http','$location', '$rootScope', menuController]);