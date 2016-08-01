/**
 * Created by Alona on 06.05.2016.
 */
function studentSettingsController($scope,studentSettingsService) {
    
    $scope.changePassword = function () {

        $scope.oldPassword;
        $scope.newPassword;
        $scope.confirmNewPassword;

        console.log($scope.oldPassword+  $scope.newPassword+$scope.confirmNewPassword);

        studentSettingsService.changePassword($scope.oldPassword, $scope.newPassword).then(function success(data) {
            console.log(data.data);
        });
    };
}

angular.module('appStudentSettings').directive('nxEqual', function() {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, model) {
            if (!attrs.nxEqual) {
                console.error('nxEqual expects a model as an argument!');
                return;
            }
            scope.$watch(attrs.nxEqual, function (value) {
                model.$setValidity('nxEqual', value === model.$viewValue);
            });
            model.$parsers.push(function (value) {
                var isValid = value === scope.$eval(attrs.nxEqual);
                model.$setValidity('nxEqual', isValid);
                return isValid ? value : undefined;
            });
        }
    };
});

angular.module('appStudentSettings')
    .controller('studentSettingsController', ['$scope', 'studentSettingsService', studentSettingsController]);
