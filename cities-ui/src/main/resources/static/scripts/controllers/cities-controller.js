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

        $scope.refresh = function() {
            $scope.firstItemIndex = ($scope.itemsPerPage * ($scope.pageNumber - 1)) + 1;
            $scope.lastItemIndex = Math.min(($scope.firstItemIndex + $scope.itemsPerPage) - 1, $scope.pagedCities.page.totalElements);

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
            $scope.itemsPerPageOptions = [5, 10, 15, 25, 50];
            $scope.searchCity = {
                name: ""
            };
            $scope.pagedCities = {
                "contents": [],
                "page": {
                    "number": 0,
                    "size": 1,
                    "totalElements": 1,
                    "totalPages": 1
                }
            };

            $scope.refresh();
        };
    });
