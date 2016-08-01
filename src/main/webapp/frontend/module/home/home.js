'use strict';
angular.module('appHome', ['appHeader', 'appFooter', 'appMenuMain', "appSecondHeader", 'appAuthorization']);


app.directive('appHeader', function () {
    return {
        templateUrl: 'module/header/header.html',
        controller: 'headerController'
    };
});

app.directive('appSecondHeader', function () {
    return {
        templateUrl: 'module/home/secondheader/secondheader.html',
        controller: 'secondHeaderController'
    };
});

app.directive('appMenuMain', function () {
    return {
        templateUrl: 'module/home/menu/mainMenu.html',
        controller: 'mainMenuController'
    };
});

app.directive('appFooter', function () {
    return {
        templateUrl: 'module/footer/footer.html',
        controller: 'footerController'
    };
});

app.directive('navMenu', function ($location) {
        return function (scope, element, attrs) {
            var links = element.find('a'),
                link,
                currentLink,
                urlMap = {},
                i;
            for (i = 0; i < links.length; i++) {
                link = angular.element(links[i]);
                urlMap[link.attr('href')] = link;
            }

            scope.$on('$routeChangeStart', function () {
                var pathLink = urlMap[$location.path()];

                if (pathLink) {
                    if (currentLink) {
                        curremtLink.removeClass('on');
                    }
                    currentLink = pathLink;
                    currentLink.addClass('on');
                }
            });
        };
    });


