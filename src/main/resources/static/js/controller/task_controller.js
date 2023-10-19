'use strict';

angular.module('myApp').controller('TaskController', ['$scope', 'TaskService', function($scope, TaskService) {
    var self = this;
    self.task = {title: null, description: null, status: 'TODO'};
    self.tasks = [];

    self.createTask = createTask;
    // self.edit = edit;
    // self.remove = remove;
    // self.reset = reset;


    fetchTasks();

    function fetchTasks() {
        TaskService.fetchTasks()
            .then(
                function (d) {
                    self.tasks = d;
                    self.task.title = null
                },
                function (errResponse) {
                    console.error('Error while fetching Tasks');
                }
            );
    }

    function createTask(){
        if (self.task.title === null)
            return;
        TaskService.createTask(self.task)
            .then(
                fetchTasks,
                function(errResponse){
                    console.error('Error while creating Task');
                }
            );
    }
}]);