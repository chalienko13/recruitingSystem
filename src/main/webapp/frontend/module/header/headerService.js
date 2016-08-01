/**
 * Created by IO on 03.05.2016.
 */

function appHeaderService($http) {
    var service = {};

    return service;

}

angular.module('appHeader')
    .service('appHeaderService', ['%http', appHeaderService]);
