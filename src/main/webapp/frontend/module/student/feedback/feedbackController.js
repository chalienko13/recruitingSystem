function feedbackController($scope, $rootScope, ngToast, feedbackService) {

    feedbackService.getFeedBack($rootScope.id).success(function (data) {
        $scope.feedBackText = data.feedBack;
    });

    $scope.saveFeedBack = function (feedBack) {
        feedbackService.saveFeedBack(feedBack).success(function (data) {
            var toastMessage = {
                content: 'Feedback updated',
                timeout: 5000,  //TODO : Change color, position
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                dismissOnClick: true,
                combineDuplications: true,
                maxNumber: 2
            };
            ngToast.success(toastMessage);
        });
    };
}


angular.module('appFeedback').controller('feedbackController', ['$scope', '$rootScope', 'ngToast', 'feedbackService', feedbackController]);
