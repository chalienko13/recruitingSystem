/**
 * Created by dima on 30.04.16.
 */

function mainController($scope, ngToast , mainService) {
    
    mainService.loadRecruitmentData().then(function success(data) {
        $scope.reqruitment = data.data;
        console.log($scope.reqruitment);
        var endRegistration = new Date(parseInt($scope.reqruitment.registrationDeadline)).toDateString();
        var endRegistrationDay = new Date(parseInt($scope.reqruitment.registrationDeadline)).getDate();
        var startInterview = new Date(parseInt($scope.reqruitment.startDate)).getDate();
        var endRecruitment = new Date(parseInt($scope.reqruitment.endDate)).getDate();

        var currentDate = new Date().getDate();
        var endDayRegistration = endRegistrationDay - parseInt(currentDate);
        var scheduleDeadline = new Date(parseInt($scope.reqruitment.scheduleChoicesDeadline)).toDateString();
        var startDayInterview = startInterview - parseInt(currentDate);

        $scope.endDayRegistrationLogo = "To registration ending";
        var str1;
        if(endDayRegistration < 0 ){
            str1 =  String(endDayRegistration).substring(1);
            $scope.endDayRegistrationLogo = "Days since registration ended";
        }else{
            str1 = endDayRegistration;
        }
        
        $scope.startDayInterviewLogo = "To the recruitment start";
        
        var str;
        if(startDayInterview < 0 ){
           str =  String(startDayInterview).substring(1);
            $scope.startDayInterviewLogo = "Days have passed since the beginning of the recruitment";
        }else{
            str = startDayInterview;
        }

        $scope.toRegEnding = str1;
        $scope.toTheInterviewStart = str;
        $scope.toTheEndOfRecruitment = endRecruitment;
    }, function error() {
        console.log("error");
    });

    mainService.getCurrentRecruitmentStudents().then(function success(data) {
        $scope.newRegistrations = data.data;
    });

}

angular.module('appMain')
    .controller('mainController', ['$scope','ngToast', 'mainService', mainController]);