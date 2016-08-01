/**
 * Created by Vova on 02.05.2016.
 */

'use strict';

function staffSchedulingService(http) {
    var service = {};

    service.getUserTimePriorities = function () {
        return http.post('/staff/getUserTimePriorities').then(function (response) {
            console.log(response.data);
            return response.data;
        });
    };

    service.getUserFinalTimePoints = function () {
        return http.get('/staff/getFinalTimePoints').then(function (response) {
            console.log(response.data);
            return response.data;
        })
    };
    
    service.saveTimePriorities = function (userTimePriorityListDto) {
        console.log("save Time Priorities");
        return http({
            method : 'POST',
            url : '/staff/saveVariants',
            data : userTimePriorityListDto
        })
    };
    
    return service;
}

angular.module('appStaffScheduling')
    .service('staffSchedulingService', ['$http', staffSchedulingService]);