/**
 * Created by dima on 30.04.16.
 */

'use strict';

function formSettingsService(http) {

    var service = {};

    service.getAllQuestionType = function () {
        return http.get('/admin/getAllQuestionType').then(function (response) {
            console.log(response.data);
            return response.data;
        });
    };

    function handleSuccess(response) {
        return response.data;
    };

    service.getAllQuestion = function (role) {
        var request = http({
            method: "GET",
            url: "/admin/getQuestions",
            params: {
                role: role
            }
        });
        return ( request.then(handleSuccess) );
    };

    service.deleteQuestion = function (id) {
        return http({
            method: "GET",
            url: "/admin/deleteQuestion",
            params: {
                id: id
            }
        });
    };
    
    
    service.changeQuestionStatus = function (id) {
        return http({
            method: "GET",
            url: "/admin/changeQuestionStatus",
            params: {
                id: id
            }
        });
    };

    service.changeQuestionMandatoryStatus = function (id) {
        return http({
            method: "GET",
            url: "/admin/changeQuestionMandatoryStatus",
            params: {
                id: id
            }
        });
    };
    
    service.addQuestion = function (question, type, enable,mandatory, formAnswerVariants, role, order) {
       return http({
            method: 'POST',
            url: '/admin/addQuestion',
            contentType: 'application/json',
            data: JSON.stringify({
                question: question,
                type: type,
                enable: enable,
                mandatory: mandatory,
                formAnswerVariants: formAnswerVariants,
                role: role,
                order: order
            })
        });
    };

    service.editQuestion = function (id, question, type, enable, formAnswerVariants, role, order,mandatory) {
        console.log(formAnswerVariants);
        console.log(order);
        http({
            method: 'POST',
            url: '/admin/updateAppFormQuestion',
            contentType: 'application/json',
            data: JSON.stringify({
                id:id,
                question: question,
                type: type,
                enable: enable,
                formAnswerVariants: formAnswerVariants,
                role: role,
                order: order,
                mandatory: mandatory
            })
        });
    };

    service.getDecisionMatrix = function() {
    	return http.get('/admin/getDecisionMatrix').then(function (response) {
            return response;
        });
    }
    service.saveDecisionMatrix = function(decisionMatrix) {
    	return http.post('../../admin/saveDecisionMatrix', decisionMatrix).then(function (response) {
            return response;
        });
    }
    
    return service;
}

angular.module('appAdminForm')
    .service('formSettingsService', ['$http', formSettingsService]);