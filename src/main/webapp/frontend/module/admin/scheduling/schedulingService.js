/**
 * Created by dima on 30.04.16.
 */
'use strict';

function schedulingService($http) {

    var ROLE_SOTF = "5";
    var ROLE_TECH = "2";
    var ROLE_STUDENT = "3";
    
    var DATES = 1;
    var TIME_POINTS = 2;
    var STAFF_SCHEDULING = 3;
    var STUDENT_SCHEDULING = 4;
    var NOT_STARTED = 5;

    var service = {};
    
    service.getCurrentStatusService = function () {
        return $http({
            method : 'GET',
            url : '/scheduling/getCurrentStatus'
        })
    };
    
    service.getCurrentRecruitmentCountStudents = function () {
        return $http.get('/scheduling/getStaffCount').then(function (response) {
            console.log(response.data);
            return response.data;
        });
    };

    service.saveSelectedDayService = function (data) {
        return $http({
            method: 'POST',
            url: '/scheduling/saveSelectedDays',
            data: data
        })
    };

    service.getSelectedDayService = function () {
        return $http({
            method: 'POST',
            url: '/scheduling/getSelectedDays'
        })
    };

    service.deleteSelectedDayService = function (id) {
        return $http({
            method: 'GET',
            url: '/scheduling/deleteSelectedDay',
            params: {id: id}
        })
    };

    service.deleteUserTimeFinal = function(userId, timePointId){
        return $http({
            method : 'POST',
            url : '/scheduling/deleteUserTimeFinal',
            params : {userId:userId,
                timePointId:timePointId}
        })
    };

    service.editSelectedDayService = function (data) {
        return $http({
            method: 'POST',
            url: '/scheduling/editSelectedDay',
            data: data
        })
    };


    service.getCurrentSchedule = function () {
        return $http({
            method: 'GET',
            url: '/scheduling/getCurrentSchedule'
        })
    };

    service.getUsersWithoutInterview = function (roleId) {
        return $http({
            method: 'GET',
            url: '/scheduling/getUsersWithoutInterview',
            params: {roleId: roleId}
        })
    };

    service.addUserToTimepoint = function (id,timePoint) {
        return $http({
            method: 'POST',
            url: '/scheduling/addUserToTimepoint',
            params: {userId:id, idTimePoint:timePoint}
        })
    };

    service.getSoftService = function (idTimePoint) {
        return $http({
            method: 'GET',
            url : '/scheduling/getUsersByTimePoint',
            params : {
                idRole : ROLE_SOTF,
                idTimePoint : idTimePoint
            }
        })
    };

    service.getTechService = function (idTimePoint) {
        return $http({
            method: 'GET',
            url : '/scheduling/getUsersByTimePoint',
            params : {
                idRole : ROLE_TECH,
                idTimePoint : idTimePoint
            }
        })
    };

    service.getStudentService = function (idTimePoint) {
        return $http({
            method: 'GET',
            url : '/scheduling/getUsersByTimePoint',
            params : {
                idRole : ROLE_STUDENT,
                idTimePoint : idTimePoint
            }
        })
    };

    service.confirmDaysSelectionService = function () {
        return $http({
            method : 'GET',
            url : '/scheduling/changeSchedulingStatus',
            params : {
                idStatus : DATES
            }
        })
    };
    
    service.cancelDaysSelectionService = function () {
        return $http({
            method : 'GET',
            url : '/scheduling/cancelSchedulingStatus',
        })
    };

    service.confirmInterviewParametersService = function () {
        return $http({
            method : 'GET',
            url : '/scheduling/changeSchedulingStatus',
            params : {
                idStatus : TIME_POINTS
            }
        })
    };

    
    service.saveInterviewParametersService = function (softDuration, techDuration) {
        return $http({
            method : 'GET',
            url : '/scheduling/saveInterviewParameters',
            params : {
                softDuration : softDuration,
                techDuration : techDuration
            }
        })
    };
    
    service.startSchedulingService = function () {
        return $http({
            method : 'GET',
            url : '/scheduling/startScheduling'
        })
    };
    
    service.getInterviewParametersService = function () {
        return $http({
            method : 'GET',
            url : '/scheduling/getInterviewParameters'
        })
    };
    
    service.getConfirmDaysSelectionStatus = function(){
        return DATES;
    };
    
    service.getConfirmInterviewParametersStatus = function () {
        return TIME_POINTS;
    };
    
    return service;
}

angular.module('appScheduling')
    .service('schedulingService', ['$http', schedulingService]);