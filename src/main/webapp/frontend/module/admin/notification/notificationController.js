/**
 * Created by dima on 30.04.16.
 */
function notificationController($scope,ngToast, http, notificationService) {

    var getInfoToast = function (message) {
        var myToastMsg = ngToast.info({
            content: message,
            timeout: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            dismissOnClick: true,
            combineDuplications: true,
            maxNumber: 2
        });
    };

    var getWarningToast = function (message) {
        var myToastMsg = ngToast.warning({
            content: message,
            timeout: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            dismissOnClick: true,
            combineDuplications: true,
            maxNumber: 2
        });
    };

    notificationService.getAllNotificationType().then(function success(data) {
        console.log(data);
        $scope.types = data;
    });

    $scope.showSelectValue = function (selectedType) {
        http({
            method: 'GET',
            url: '/admin/showTemplate',
            params: {title: selectedType}
        }).success(function (data) {
            console.log(data);
            $scope.topic = data.title;
            $scope.textNotification = data.text;
            $scope.emailTemplate = data;
            return data;
        });
    };

    $scope.save = function () {
        console.log($scope.emailTemplate.title);
        $scope.emailTemplate.title = $scope.topic;
        $scope.emailTemplate.text =  $scope.textNotification;
        console.log($scope.emailTemplate);
        notificationService.saveNotification($scope.emailTemplate).then(function success(data) {
            if (data.status === 200) {
                getInfoToast("Notification updated");
            }
        }).catch(function () {
            getWarningToast("Notification not added");
        });
    }

}

angular.module('appNotification')
    .controller('notificationController', ['$scope','ngToast', '$http', 'notificationService', notificationController]);