/**
 * Created by BIG BOSS on 11.05.2016.
 */

'use strict';

function confirmRegistrationService(http) {

    var service = {};

    service.confirmRegistration = function (routeParam) {
        return http({
            method : 'GET',
            url : '/registrationStudent/'+routeParam
        })
    };

    return service;
}

angular.module('appConfirmRegistration')
    .service('confirmRegistrationService', ['$http', confirmRegistrationService])
