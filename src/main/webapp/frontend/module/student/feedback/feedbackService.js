'use strict';

function feedbackService($http) {
    var service = {};


    service.getFeedBack = function (id) {
        return $http({
            method: 'GET',
            url: '/student/getFeedBack',
            params: {id: id}
        }).error(function (data, status, headers) {
            console.log(status);
        });
    };

    service.saveFeedBack = function (feedBack) {
        return $http({
            method: 'POST',
            url: '/student/saveFeedBack',
            contentType: 'application/json',
            params: {feedBack: feedBack}
        })
    };
    
    return service;
}

angular.module('appFeedback')
    .service('feedbackService', ['$http', feedbackService]);