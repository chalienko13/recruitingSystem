/**
 * Created by Admin on 12.05.2016.
 */
function adminStudentFormController($scope,$http,ngToast, studentAppFormService, $routeParams) {

    $scope.showAppForm = function showAppForm() {
        var id = $routeParams.id;
        studentAppFormService.getAppForm(id).success(function (data) {
            $scope.appForm = data;
            console.log(data);
            var appFormId = $scope.appForm.id;
            studentAppFormService.getNonAdequateMark(appFormId).success( function(data){
                console.log(data);
                $scope.adequate = data;
            });
            studentAppFormService.getRolesInt(appFormId).success(function (data) {
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

        studentAppFormService.getOldApplicationForms(id).success(function (data) {
            if(data.length>0)
                $scope.oldForms = [];
            for(var i=0; i< data.length; i++){
                $scope.oldForms[i]=angular.fromJson(data[i]);
            }
        })
    };

    $scope.oldToShow = 0;
    $scope.changeTab = function(tab){
        $scope.tab = tab;
    };

    $scope.showInterview = function showInterview(role) {
        var appFormId = $scope.appForm.id;
        studentAppFormService.getInterview(appFormId,role).success(function (data) {
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
            url: "/admin/appForm/"+ AppForm.id,
            headers: {
                'Accept': 'application/pdf'
            }
        };
        $http(config)
            .success(function(){
                window.location = "/admin/appForm/"+ AppForm.id;
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

angular.module('appFormStudents')
    .controller('adminStudentFormController', ['$scope','$http','ngToast','studentAppFormService', '$routeParams',adminStudentFormController]);