/**
 * Created by dima on 29.04.16.
 */
'use strict';

function studentManagementService(http) {

    var service = {};

    service.showAllStudents = function (pageNum, rowsNum, sortingCol, increase) {
        console.log("Service showAllStudents");
        return http({
            method: 'GET',
            url: '/admin/showAllStudents',
            params: {pageNum: pageNum, rowsNum: rowsNum, sortingCol: sortingCol, increase: increase}
        })
    };

    service.getAllStatuses = function () {
        return http({
            method : 'GET',
            url : '/admin/getAllStatuses'
        })
    };

    service.changeSelectedStatuses = function (changeStatus, appFormIdList) {
        return http({
            method: 'POST',
            url: '/admin/changeSelectedStatuses',
            params: {
                changeStatus: changeStatus,
                appFormIdList: appFormIdList
            }
        })
    };

    service.changeStatus = function (changeStatus, appFormId) {
        return http({
            method: 'POST',
            url: '/admin/changeStatus',
            params: {
                changeStatus: changeStatus,
                appFormId: appFormId
            }
        })
    };

    service.getCountOfStudents = function () {
        return http({
            method: 'GET',
            url: '/admin/getCountOfStudents'
        })
    };

    service.getRejectCount = function () {
        return http({
            method: 'GET',
            url: '/admin/getRejectCount'
        })
    };

    service.getJobCount = function () {
        return http({
            method: 'GET',
            url: '/admin/getJobCount'
        })
    };

    service.getAdvancedCount = function () {
        return http({
            method: 'GET',
            url: '/admin/getAdvancedCount'
        })
    };

    service.getGeneralCount = function () {
        return http({
            method: 'GET',
            url: '/admin/getGeneralCount'
        })
    };

    service.getApprovedCount = function () {
        return http({
            method: 'GET',
            url: '/admin/getApprovedCount'
        })
    };
    
    service.getTimePoints = function() {
    	 return http({
             method: 'GET',
             url: '/admin/getTimePoints'
         })
    };
    
    service.confirmSelection = function () {
        return http({
            method: 'POST',
            url: '/admin/confirmSelection'
        })
    };

    service.announceResults = function() {
    	 return http({
             method: 'POST',
             url: '/admin/announceResults'
         })
    };
    
    service.calculateStatuses = function() {
   	 return http({
            method: 'POST',
            url: '/admin/calculateStatuses'
        })
   }
    
    service.searchStudent = function (lastName,pageNum,rowsNum) {
        return http({
            method: 'POST',
            url: '/admin/searchStudent',
            params: {
                lastName: lastName,
                pageNum: pageNum,
                rowsNum: rowsNum
            }
        })
    };

    service.getAllQuestions = function () {
        var request = http({
            method: "POST",
            url: "/admin/getapplicationquestionsnontext",
            params: {
                role: "ROLE_STUDENT"
            }
        });
        return ( request.then(handleSuccess) );
    };

    service.showFilteredStudents = function (filtrationParams) {
        console.log("Service showFilteredStudents");
        return http({
            method: 'POST',
            url: '/admin/showFilteredStudents',
            contentType: 'application/json',
            data: filtrationParams
        })
    };

    function handleSuccess(response) {
        return ( JSON.parse("[" + response.data + "]") );
    }

    
    service.getRecruitmentStatus = function() {
    	console.log("recruitment service");
//    	return http.get('../../admin/getRecruitmentStatus').then(function (response) {
//            return response;
//        });
    	 return http({
             method: 'GET',
             url: '/admin/getRecruitmentStatus',
         })
    }
    
    
    return service;
}

angular.module('appStudentManagement')
    .service('studentManagementService', ['$http', studentManagementService]);