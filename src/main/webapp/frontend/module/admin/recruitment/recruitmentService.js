/**
 * Created by dima on 30.04.16.
 */
'use strict';

function recruitmentService(http) {

    var service = {};

    service.addRecruitment  = function (name,registrationDeadline, scheduleChoicesDeadline, endDate) {
        console.log(registrationDeadline+scheduleChoicesDeadline);
       return http({
            method : 'POST',
            url : '/admin/addRecruitment',
            contentType: 'application/json',
            data : JSON.stringify({
                name:name,
                registrationDeadline: registrationDeadline,
                scheduleChoicesDeadline: scheduleChoicesDeadline,
                endDate: endDate
            })
        });
    };

    service.editRecruitment  = function (id, name,registrationDeadline, scheduleChoicesDeadline, endDate) {
       return http({
            method : 'POST',
            url : '/admin/editRecruitment',
            contentType: 'application/json',
            data : JSON.stringify({
                id: id,
                name:name,
                registrationDeadline: registrationDeadline,
                scheduleChoicesDeadline: scheduleChoicesDeadline,
                endDate: endDate
            })
        });
    };
 
    service.endRecruitment = function () {
        console.log("End Recruitment");
         http.get('/admin/endRecruitment').then(function (response) {
            return response;
        });
    };

    service.getCurrentRecruitment = function () {
       return  http.get('/admin/getCurrentRecruitment').then(function (response) {
            console.log(response.data);
            return response.data;
        });
    };
        
    return service;
}

angular.module('appRecruitment')
    .service('recruitmentService', ['$http', recruitmentService]);