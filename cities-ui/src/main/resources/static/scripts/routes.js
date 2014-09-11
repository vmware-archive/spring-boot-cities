'use strict';

angular.module('citiesUiApp')
    .config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/cities');

        $stateProvider.state('cities', {
            url: '/cities',
            controller: 'CitiesController',
            templateUrl: 'views/cities.html'
        });
    });