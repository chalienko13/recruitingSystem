/**
 * Created by dima on 30.04.16.
 */

'use strict';

var app = angular.module('appMain', ['appMenu','appHeader','appFooter','angular-loading-bar']);
app.directive('appMenuAdmin', function () {
    return {
        templateUrl: 'module/admin/view/menu.html',
        controller: 'menuController'
    };
});
