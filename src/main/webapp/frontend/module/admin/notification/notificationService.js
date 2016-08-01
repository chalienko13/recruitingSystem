/**
 * Created by dima on 30.04.16.
 */
'use strict';

function notificationService(http) {

    var service = {};

    service.getAllNotificationType = function () {
        return http.get('/admin/getAllNotificationType').then(function (response) {
            return response.data;
        });
    };

    service.showTemplate = function (title) {
      return  http({
            method : 'GET',
            url : '/admin/showTemplate',
            params : {title:title}
        }).success(function (data) {
            console.log(data);
            return data;
        });
    };

    service.saveNotification = function (emailTemplate) {
       return http({
            method : 'POST',
            url : '/admin/changeNotification',
            data : emailTemplate
        }).success(function (data) {
            console.log(data);
            return data;
        });
    };

    return service;
}

angular.module('appNotification')
    .service('notificationService', ['$http', notificationService]);