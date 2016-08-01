/**
 * Created by dima on 02.05.16.
 */
'use strict';

function appFormService(http) {
    var service = {};

    service.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            .success(function(){
            })
            .error(function(){
            });
    };

    service.loadAppFormData = function () {
        return http.post('/student/appform').then(function (response) {
            return response.data;
        });
    };

    service.changeUserName = function (data) {
       var req =  http({
            method : 'POST',
            url : '/student/saveApplicationForm',
            contentType: 'application/json',
            data : {
                status : data.status,
                user : data.user,
                questions : data.questions
            }
        });
       var response; 
       req.success(function(data) {
            
			console.log(data);
			response =  data;
		});
       return response;
    };



    return service;
}

angular.module('appStudentForm')
    .service('appFormService', ['$http', appFormService]);