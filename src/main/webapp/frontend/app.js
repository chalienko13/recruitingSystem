/**
 * Created by dima on 29.04.16.
 */
'use strict';

angular.module('app', [
    'ngMessages',
    'ngRoute',
    'appFormStudents',
    'ngFileUpload',
    'gm.datepickerMultiSelect',
    'ui.bootstrap',
    'appHome',
    'appMain',
    'appFeedback',
    'appStaffMain',
    'appStaffPersonal',
    'appStaffScheduling',
    'appStaffStudentManagement',
    'appStaffManagement',
    'appStaffAppForm',
    'appInterview',
    'appStudentManagement',
    'appReport',
    'appScheduling',
    'appAdminForm',
    'appRecruitment',
    'appNotification',
    'appPersonal',
    'appStudentForm',
    'appStudentMenu',
    'studentScheduling',
    'appStudentSettings',
    'appAuthorization',
    'appRegistration',
    'appMenuMain',
    'appEducationNC',
    'appGetToCourses',
    'appRecoverRequestPage',
    'appRecoverPassword',
    'appConfirmRegistration',
    'appError',
    'serverError'
    
]).controller('appController', function ($scope, $http, $rootScope, TokenStorage) {
    $scope.init = function () {
        $rootScope.authenticated = false;
        $rootScope.username = null;
        $rootScope.id = null;
        $http.get('/currentUser')
            .success(function (user) {
                if (TokenStorage.retrieve() != undefined){
                    $rootScope.authenticated = true;
                    $rootScope.id = user.id;
                    $rootScope.username = user.firstName;
                    $rootScope.roles = user.roles;
                    console.log(user.roles);
                }
            })
            .error(function () {
                TokenStorage.clear();
            });
    };
}).config(function ($routeProvider, $httpProvider) {

    $httpProvider.interceptors.push('TokenAuthInterceptor');
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

    $routeProvider
        .when('/home', {
            templateUrl: 'module/home/home.html',
            controller: 'homeController'
        })
        .when('/getToCourses', {
            templateUrl: 'module/home/gettocourses/getToCourses.html',
            controller: 'getToCoursesController'
        })
        .when('/educationNC', {
            templateUrl: 'module/home/educationnc/educationNC.html',
            controller: 'educationNCController'
        })
        .when('/accessDenied', {
            templateUrl: 'module/error/accessdenied.html',
            controller: 'accessdeniedController'
        })
        .when('/registrationStudent/:token', {
            templateUrl: 'module/home/confirmregistration/confirmregistration.html',
            controller: 'confirmRegistrationController'
        })

        .when('/admin/main', {
            templateUrl: 'module/admin/view/main.html',
            controller: 'mainController'
        })
        .when('/admin/staffmanagement', {
            templateUrl: 'module/admin/view/staffmanagement.html',
            controller: 'staffManagementController'
        })
        .when('/admin/studentmanagement', {
            templateUrl: 'module/admin/view/studentmanagement.html',
            controller: 'studentManagementController'
        })
        .when('/admin/adminformsettings', {
            templateUrl: 'module/admin/view/formSettings.html',
            controller: 'formSettingsController'
        })
        .when('/admin/scheduling', {
            templateUrl: 'module/admin/view/scheduling.html',
            controller: 'schedulingController'
        })
        .when('/admin/report', {
            templateUrl: 'module/admin/view/report.html',
            controller: 'reportController'
        })
        .when('/admin/recruitment', {
            templateUrl: 'module/admin/view/recruitment.html',
            controller: 'recruitmentController'
        })
        .when('/admin/notification', {
            templateUrl: 'module/admin/view/notification.html',
            controller: 'notificationController'
        })
        .when('/admin/personal', {
            templateUrl: 'module/admin/view/personal.html',
            controller: 'personalController'
        })
        .when('/admin/studentform/:id', {
            templateUrl: 'module/admin/view/studentappform.html',
            controller: 'adminStudentFormController'
        })

        //double roles
        .when('/admin/studentManagement', {
            templateUrl: 'module/staff/view/staffStudentManagement.html',
            controller: 'staffStudentManagementController'
        })

        .when('/admin/interview', {
            templateUrl: 'module/staff/view/interview.html',
            controller: 'interviewController'
        })

        .when('/admin/staffScheduling', {
            templateUrl: 'module/staff/view/staffScheduling.html',
            controller: 'staffSchedulingController'
        })

        //STAFF
        .when('/staff/main', {
            templateUrl: 'module/staff/view/staffMain.html',
            controller: 'staffMainController'
        })

        .when('/staff/studentManagement', {
            templateUrl: 'module/staff/view/staffStudentManagement.html',
            controller: 'staffStudentManagementController'
        })

        .when('/staff/interview', {
            templateUrl: 'module/staff/view/interview.html',
            controller: 'interviewController'
        })

        .when('/staff/scheduling', {
            templateUrl: 'module/staff/view/staffScheduling.html',
            controller: 'staffSchedulingController'
        })

        .when('/staff/personal', {
            templateUrl: 'module/staff/view/staffPersonal.html',
            controller: 'staffPersonalController'
        })
        .when('/staff/appformstudent/:id', {
            templateUrl: 'module/staff/view/appFormStudent.html',
            controller: 'appFormStudentController'
        })

        // STUDENT
        .when('/student/appform', {
            templateUrl: 'module/student/view/appForm.html',
            controller: 'appFormController'
        })
        .when('/student/feedback', {
            templateUrl: 'module/student/view/feedback.html',
            controller: 'feedbackController'
        })
        .when('/student/scheduling', {
            templateUrl: 'module/student/view/studentScheduling.html',
            controller: 'studentSchedulingController'
        })

        .when('/student/settings', {
            templateUrl: 'module/student/view/studentSettings.html',
            controller: 'studentSettingsController'
        })

        //Auth
        .when('/authorization', {
            templateUrl: 'module/home/authorization/authorization.html',
            controller: 'authorizationController'

        })

        .when('/registration', {
            templateUrl: 'module/home/registration/registration.html',
            controller: 'registrationController'
        })

        //recoverRequestPassword
        .when('/recoverPasswordRequest', {
            templateUrl: 'module/home/recoverRequestPage/recoverRequestPage.html',
            controller: 'recoverRequestPageController'
        })

        .when('/recoverPasswordPage', {
            templateUrl: 'module/home/recoverPasswordPage/recoverPasswordPage.html',
            controller: 'recoverPasswordPageController'
        })

        .when('/serverError', {
            templateUrl: 'module/error/serverError.html',
            controller: 'serverErrorController'
        })

    .
    otherwise({
        redirectTo: '/home'
    });
});
