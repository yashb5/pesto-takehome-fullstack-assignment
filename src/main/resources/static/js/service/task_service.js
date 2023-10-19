'use strict';

angular.module('myApp').factory('TaskService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8080/api/v1/todo';

    var factory = {
        fetchTasks: fetchTasks,
        createTask: createTask,
        updateTask:updateTask,
        // deleteUser:deleteUser
    };

    return factory;

    function fetchTasks(filter) {
        var deferred = $q.defer();
        console.log(filter)
        //let params = new HttpParams().set("status", filter)
        var filterUrl = ""
        if (filter !== 'ALL') {
            filterUrl =  "?status=" + filter;
        }

        $http.get(REST_SERVICE_URI + filterUrl)
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

    function createTask(task) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, task)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while creating User');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function updateTask(id, taskStatus) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+'/'+id, {'status': taskStatus})
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while updating Task');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}]);