var app = angular.module('ShutdownDemo', ['ngResource']);

app.factory('Status', ['$resource', function($resource) {
    return $resource('status');
}]);

app.controller('StatusController', ['$scope', '$interval', 'Status', function($scope, $interval, Status) {
    $scope.status = {};
    $scope.counter = 0;

    $scope.refreshStatus = function() {
        $scope.status = Status.get(
            function () {
            },
            function (result) {
                refresh.cancel();
            }
        );
    };

    var refresh = $interval(function() {
        $scope.refreshStatus();
        $scope.counter++;
    }, 1000);
}]);
