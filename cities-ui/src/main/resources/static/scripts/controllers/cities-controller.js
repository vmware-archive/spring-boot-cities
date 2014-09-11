'use strict';

angular.module('citiesUiApp', ['ngResource', 'ui.bootstrap']).
    factory('PostalCodes', function ($resource) {
        return $resource('cities');
    }).
    factory('PostalCodesSearch', function ($resource) {
        return $resource('cities/search');
    }).
    factory('PostalCode', function ($resource) {
        return $resource('cities/:id', {id: '@id'});
    }).
    controller('CitiesController', function ($scope, PostalCodes, PostalCodesSearch, PostalCode) {
        function list() {
            PostalCodes.get({page: $scope.pageNumber - 1, size: $scope.itemsPerPage}).$promise.then(function(pagedCities) {
                $scope.pagedCities = pagedCities;
            })
        }

        function search() {
            PostalCodesSearch.get({name: $scope.searchCity.name, page: $scope.pageNumber - 1, size: $scope.itemsPerPage}).$promise.then(function(pagedCities) {
                $scope.pagedCities = pagedCities;
            })
        }

        $scope.pageChanged = function() {
            if ($scope.searchCity.name)
                search();
            else
                list();
        };

        $scope.search = function() {
            search();
        };

        $scope.clearSearch = function() {
            $scope.searchCity.name = "";
            list();
        };

        $scope.init = function() {
            $scope.pageNumber = 1;
            $scope.itemsPerPage = 15;
            $scope.maxPageLinks = 15;
            $scope.searchCity = {
                name: ""
            };
            list();
        };
    });
