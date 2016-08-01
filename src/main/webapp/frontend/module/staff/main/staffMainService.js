/**
 * Created by Vova on 02.05.2016.
 */

'use strict';


function staffMainService(http) {

    var service = {};

    service.loadRecruitmentData = function () {
        return http.post('/staff/recruitment').then(function (response) {
            return response;
        });
    };

    return service;
}
angular.module('appStaffMain')
    .service('staffMainService', ['$http',staffMainService]);