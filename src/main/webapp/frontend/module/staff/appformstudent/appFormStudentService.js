/**
 * Created by Alona on 06.05.2016.
 */
'use strict';

function appFormStudentService(http) {
    var service = {};
    service.loadAppFormData = function () {
        return http.post('/staff/appform').then(function (response) {
            return response.data;
        });
    };

    service.getAppForm = function (id) {
        console.log("Service getAppForm");
        return http({
            method: 'POST',
            url: '/staff/getApplicationForm/' + id
        })
    };
    service.getInterview = function (appFormId, role) {
        console.log("Service getInterview");
        return http({
            method: 'POST',
            url: '/staff/getInterview/' + appFormId + '/' + role
        })
    };
    service.getRoles = function (appFormId) {
        console.log("Service getRoles");
        return http({
            method: 'GET',
            url: '/staff/getRoles/'+ appFormId
        })
    };

    service.getNonAdequateMark = function (appFormId) {
        console.log("Service getAdequateMark");
        return http({
            method: 'GET',
            url: '/staff/getAdequateMark/'+ appFormId
        })
    };
    service.submitInterview = function (data) {
        var req =  http({
            method : 'POST',
            url : '/staff/submitInterview',
            contentType: 'application/json',
            data : {
                adequateMark: data.adequateMark,
                applicationForm: data.applicationForm,
                date: data.date,
                id: data.id,
                interviewer: data.interviewer,
                mark: data.mark,
                questions: data.questions,
                role: data.role
            }
        });
        var response;
        req.success(function(data) {
            response =  data;
        });
        return response;
    };

    return service;
}

angular.module('appStaffAppForm')
    .service('appFormStudentService', ['$http', appFormStudentService]);