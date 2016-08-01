/**
 * Created by dima on 30.04.16.
 */
function recruitmentController($scope, ngToast, recruitmentService) {

    var getSuccessToast = function (message) {
        var myToastMsg = ngToast.success({
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

    var dateFormat = 'yyyy-MM-dd HH:mm';

    var nowTime = new Date().toString(dateFormat);

    var nowTimeMilis = new Date();
    recruitmentService.getCurrentRecruitment().then(function success(data) {

        $scope.recId = data.id;
        if ($scope.recId == undefined) {
            $scope.newRecruitment = true;

            $scope.registrationDeadline = nowTime;

            $scope.scheduleChoicesDeadline = nowTime;

            $scope.endDate = nowTime;
            console.log(nowTime);
        } else {


        console.log(data.data);


        $scope.registrationDeadline = new Date(parseInt(data.registrationDeadline)).toString(dateFormat);
            if (data.registrationDeadline < nowTimeMilis) {
                $scope.passedRegDeadline = true;
            }else {
                $scope.passedRegDeadline = false;
            }
        
        $scope.scheduleChoicesDeadline = new Date(parseInt(data.scheduleChoicesDeadline)).toString(dateFormat);
            if (data.scheduleChoicesDeadline < nowTimeMilis) {
                $scope.passedScheduleDeadline = true;
            }else {
                $scope.passedScheduleDeadline = false;
            }

        
        $scope.endDate = new Date(parseInt(data.endDate)).toString(dateFormat);

        $scope.recruitmentName= data.name;

        console.log($scope.registrationDeadline);
        console.log($scope.scheduleChoicesDeadline);
        console.log($scope.endDate);}
    });


    var zeroSeconds = ':00';

    $scope.edit = function () {

        console.log($scope.registrationDeadline);
        console.log($scope.scheduleChoicesDeadline);
        console.log($scope.endDate);
        recruitmentService.editRecruitment($scope.recId, $scope.recruitmentName, $scope.registrationDeadline + zeroSeconds, 
            $scope.scheduleChoicesDeadline + zeroSeconds, $scope.endDate+ zeroSeconds).then(function (response) {
            if (response.status === 200) {
                getSuccessToast('Recruitment Updated');
            }
        })
            .catch(function (response) {
                console.log(response);
                getWarningToast(response.data.message);
            });

    };
    $scope.save = function () {
        console.log($scope.registrationDeadline);
        console.log($scope.scheduleChoicesDeadline);
        console.log($scope.endDate);
        recruitmentService.addRecruitment($scope.recruitmentName, $scope.registrationDeadline + zeroSeconds,
            $scope.scheduleChoicesDeadline+ zeroSeconds, $scope.endDate+ zeroSeconds).then(function (response) {
                if (response.status === 200) {
                    getSuccessToast('Recruitment Created');
                }
            })
            .catch(function (response) {
                console.log(response);
                getWarningToast(response.data.message);
            });
    };

    $scope.endRecruitment = function () {
        recruitmentService.endRecruitment();
    }


}

angular.module('appRecruitment')
    .controller('recruitmentController', ['$scope','ngToast', 'recruitmentService', recruitmentController]);