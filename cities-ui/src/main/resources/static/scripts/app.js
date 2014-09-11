'use strict';

angular.module('citiesUiApp', [ 'ngResource', 'ui.router' ])
    .constant('appConfiguration', {
        citiesApiUrl: window.location.protocol + '//' + window.location.host + '/proxy'
    })
    .config(function () {
    });

