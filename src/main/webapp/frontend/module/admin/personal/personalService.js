/**
 * Created by dima on 30.04.16.
 */
'use strict';

function personalService(http) {

    var service = {};

    service.changePassword = function (oldPassword, newPassword) {
        http({
            method: 'POST',
            url: '/changepassword',
            params: {oldPassword: oldPassword, newPassword: newPassword}
        }).success(function (data, status, headers) {
            return data;
        })
    };

    return service;
}

angular.module('appPersonal')
    .service('personalService', ['$http', personalService]);