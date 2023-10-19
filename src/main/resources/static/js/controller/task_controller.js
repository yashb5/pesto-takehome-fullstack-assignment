'use strict';

angular.module('myApp').controller('TaskController', ['$scope', 'TaskService', function($scope, TaskService) {
    var self = this;
    self.user = {id: null, username: '', address: '', email: ''};
    self.tasks = [];

    // self.submit = submit;
    // self.edit = edit;
    // self.remove = remove;
    // self.reset = reset;


    fetchTasks();

    function fetchTasks() {
        TaskService.fetchTasks()
            .then(
                function (d) {
                    self.tasks = d;
                },
                function (errResponse) {
                    console.error('Error while fetching Tasks');
                }
            );
    }
}]);