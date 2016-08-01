/**
 * Created by dima on 02.05.16.
 */

'use strict';

angular.module('appStudentForm', ['angular-loading-bar'])
    .directive('appMenuStudent', function () {
        return {
            templateUrl: 'module/student/view/studentMenu.html',
            controller: 'studentMenuController'
        };
    })
    .directive('myDirective', function (httpPostFactory) {
        return {
            restrict: 'A',
            link: function (scope, element, attr) {
                element.bind('change', function () {
                    var formData = new FormData();
                    formData.append('file', element[0].files[0]);

                    
                    var fileObject = element[0].files[0];
                    scope.fileLog = {
                        'lastModified': fileObject.lastModified,
                        'lastModifiedDate': fileObject.lastModifiedDate,
                        'name': fileObject.name,
                        'size': fileObject.size,
                        'type': fileObject.type
                    };
                    scope.$apply();

                });

            }
        };
    })
    .factory('httpPostFactory', function ($http) {
        return function (file, data, callback) {
            $http({
                url: file,
                method: "POST",
                data: data,
                headers: {
                    'Content-Type': undefined
                }
            }).success(function (response) {
                callback(response);
            });
        };
    });

