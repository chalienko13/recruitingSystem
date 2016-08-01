/**
 * Created by Alona on 06.05.2016.
 */
'use strict';

function studentSettingsService(http) {

    var service = {};

    service.changePassword = function (oldPassword, newPassword) {

        http({
            method : 'POST',
            url : '/changepassword',
            params : {oldPassword:oldPassword, newPassword:newPassword}
        }).success(function (data, status, headers) {
            return data;
        })
    };

    return service;
}

angular.module('appStudentSettings')
    .service('studentSettingsService', ['$http', studentSettingsService]);
