/**
 * Created by dima on 30.04.16.
 */
'use strict';

function authorizationService($http) {

    var service = {};

    service.loginIn = function (email, password) {

    };

    return service;
}

angular.module('appAuthorization')
    .service('authorizationService', ['$http', authorizationService])