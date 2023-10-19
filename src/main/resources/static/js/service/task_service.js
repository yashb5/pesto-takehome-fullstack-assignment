'use strict';

angular.module('myApp').factory('TaskService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8080/api/v1/todo';

    var factory = {
        fetchTasks: fetchTasks,
        // createUser: createUser,
        // updateUser:updateUser,
        // deleteUser:deleteUser
    };

    return factory;

    function fetchTasks() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching Tasks');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}]);