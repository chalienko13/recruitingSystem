/**
 * Created by dima on 01.05.16.
 */
'use strict';

function mainService(http) {
    
    var service = {};

    service.loadRecruitmentData = function () {
        return http.post('/admin/recruitment').then(function (response) {
            return response;
        });
    };

    service.getCurrentRecruitmentStudents = function () {
        return http.get('/admin/getCurrentRecruitmentStudents').then(function (response) {
            return response;
        });
    };

    return service;
}

angular.module('appMain')
    .service('mainService', ['$http', mainService]);