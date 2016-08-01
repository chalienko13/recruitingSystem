/**
 * Created by dima on 30.04.16.
 */
'use strict';

angular.module('appAuthorization', ['angular-loading-bar'])
    .factory('TokenStorage', function ($rootScope) {
        var storageTokenLP = 'auth-tokenLP';
        var storageTokenSN = 'auth-tokenSN';
        
        
        return {
            store: function (token, title) {
                $rootScope.authenticated = true;
                if(title === 'X-AUTH-TOKEN_LOGIN_PASSWORD'){
                    return localStorage.setItem(storageTokenLP, token);
                }else if (title === 'X-AUTH-TOKEN_SOCIAL') {
                    return localStorage.setItem(storageTokenSN, token);
                }
                
            },
            retrieve: function () {
                if (localStorage.getItem(storageTokenLP) != undefined){
                    return localStorage.getItem(storageTokenLP);
                }else if (localStorage.getItem(storageTokenSN) != undefined) {
                    return localStorage.getItem(storageTokenSN);
                }else {
                    return undefined;
                }
            },
            clear: function () {
                $rootScope.authenticated = false;
                $rootScope.username = null;
                $rootScope.id = null;
                localStorage.removeItem(storageTokenSN);
                return localStorage.removeItem(storageTokenLP);
                
            }
        };
    }).factory('TokenAuthInterceptor', function ($q, TokenStorage, $location) {
    var storageTokenLP = 'auth-tokenLP';
    var storageTokenSN = 'auth-tokenSN';
    return {
        request: function (config) {
            var authToken = TokenStorage.retrieve();
            if (authToken){
                if (localStorage.getItem(storageTokenLP) != undefined){
                    config.headers['X-AUTH-TOKEN_LOGIN_PASSWORD'] = authToken;
                }else if (localStorage.getItem(storageTokenSN) != undefined) {
                    config.headers['X-AUTH-TOKEN_SOCIAL'] = authToken;
                }else {
                    return config;
                }
            }
            return config;
        },
        responseError: function (error) {
            if (error.status === 403) {
                $location.path('/accessDenied');
            } else if (error.status === 500) {
                // $location.path("/serverError");
            } else if (error.status === 504) {
                // alert(error.data.message);
            }
            return $q.reject(error);
        }
    };
});
VK.init(function() {
    apiId:5484909   
}, function() {

}, '5.52');

window.fbAsyncInit = function() {
    FB.init({
        appId      : '586710581492768',
        xfbml      : true,
        version    : 'v2.6'
    });
};


(function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));