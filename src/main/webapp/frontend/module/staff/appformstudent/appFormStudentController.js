/**
 * Created by Alona on 06.05.2016.
 */
function appFormStudentController($scope,$http,ngToast, appFormStudentService,$routeParams) {

    $scope.showAppForm = function showAppForm() {
        var id = $routeParams.id;
            appFormStudentService.getAppForm(id).success(function (data) {
                $scope.appForm = data;
                console.log(data);
                var appFormId = $scope.appForm.id;
                appFormStudentService.getNonAdequateMark(appFormId).success( function(data){
                    console.log(data);
                    $scope.adequate = data;
                });
                appFormStudentService.getRoles(appFormId).success(function (data) {
                    $scope.roles = data;
                    console.log(data);
                    $scope.roleTech = false;
                    $scope.roleSoft = false;
                    for(var i=0; i< data.length; i++){

                        if(data[i].id==2){
                            $scope.roleTech = true;
                        }

                        if(data[i].id==5){
                            $scope.roleSoft = true;
                        }
                    }
                    console.log($scope.roleTech);
                    console.log($scope.roleSoft);
                }, function error() {
                    console.log("error");
                });
            }, function error() {
                console.log("error");
            });

    };

    $scope.showInterview = function showInterview(role) {
        var appFormId = $scope.appForm.id;
        appFormStudentService.getInterview(appFormId,role).success(function (data) {
            console.log(data);
            var role = data.role;
            console.log(role);
            if(role == 2) {
                $scope.interviewTech = data;

            }else if (role == 5){
                $scope.interviewSoft = data;
            }
        }, function error() {
            console.log("error");
        });
    };

    $scope.submitInterview = function submitInter(interviewTech) {
        appFormStudentService.submitInterview(interviewTech).success(function (data) {
            console.log(data);

        }, function error() {
            console.log("error");
        });
    };
    $scope.submitInter = function (data) {
        var req =  $http({
            method : 'POST',
            url : '/staff/submitInterview',
            contentType: 'application/json',
            data : {
                adequateMark: data.adequateMark,
                applicationForm: data.applicationForm,
                date: data.date,
                id: data.id,
                interviewer: data.interviewer,
                mark: parseInt(data.mark),
                questions: data.questions,
                role: data.role
            }
        });
        var response;
        req.success(function(data) {
            response =  data;
            $scope.resultMessage =  data;
			var toastMessage = {
	                content: $scope.resultMessage.message,
	                timeout: 5000,  
	                horizontalPosition: 'center',
	                verticalPosition: 'bottom',
	                dismissOnClick: true,
	                combineDuplications: true,
	                maxNumber: 2
	            };
			if ($scope.resultMessage.type == 'ERROR') {
				var myToastMsg = ngToast.warning(toastMessage);
			}
			else if ($scope.resultMessage.type == 'SUCCESS') {
				var myToastMsg = ngToast.success(toastMessage);
			}
        });
        return response;
    };
    $scope.toggle = function (item, list){
        var idx=-1;
        for(var i=0; i<list.length; i++){
            if(list[i].answer==item)
                idx=i;
        }
        if (idx >-1){
            list.splice(idx,1);
        }
        else {
            list.push({answer: item});
        }
        console.log(list);
    };

    $scope.toggleT = function (item){
        var i = item;
        if (i == true){
            $scope.interviewTech.adequateMark = false;
        } else if (i == false){
            $scope.interviewTech.adequateMark = true;
        }
    };
    $scope.toggleS = function (item){
        var i = item;
        if (i == true){
            $scope.interviewSoft.adequateMark = false;
        } else if (i == false){
            $scope.interviewSoft.adequateMark = true;
        }
    };

    $scope.exists = function (item, list){
        for(var i=0; i<list.length; i++){
            if(list[i].answer==item)
                return true;
        }
        return false;
    };

    $scope.exportAppForm = function(AppForm){
        var config = {
            method: 'GET',
            url: "/staff/appForm/"+ AppForm.id,
            headers: {
                'Accept': 'application/pdf'
            }
        };
        $http(config)
            .success(function(){
                window.location = "/staff/appForm/"+ AppForm.id;
            })
            .error(function(){
                var myToastMsg = ngToast.warning({
                    content: 'Error exporting Application Form ',
                    timeout: 5000,  //TODO : Change color, position
                    horizontalPosition: 'center',
                    verticalPosition: 'bottom',
                    dismissOnClick: true,
                    combineDuplications: true,
                    maxNumber: 2
                });
            });
    };


}



angular.module('appStaffAppForm')
    .controller('appFormStudentController', ['$scope','$http','ngToast','appFormStudentService', '$routeParams', appFormStudentController]);
