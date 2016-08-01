/**
 * Created by BOSS on 11.05.2016.
 */
function confirmRegistrationController($scope, $routeParams, confirmRegistrationService) {

    $scope.foo = true;
    $scope.message = '';

    $scope.init = function () {
        console.log();
    };

    console.log($routeParams);
    confirmRegistrationService.confirmRegistration($routeParams.token)
        .then(function (response) {
            $scope.foo = true;
        })
        .catch(function (response) {
            if (response.status === 409){
                $scope.foo = false;
                $scope.message = response.data.message;
            }
        });

}

angular.module('appConfirmRegistration')
    .controller('confirmRegistrationController', ['$scope', '$routeParams', 'confirmRegistrationService', confirmRegistrationController]);