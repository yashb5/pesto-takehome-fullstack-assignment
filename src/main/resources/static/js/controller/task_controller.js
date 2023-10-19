'use strict';

angular.module('myApp').controller('TaskController', ['$scope', 'TaskService', function($scope, TaskService) {
    var self = this;
    self.task = {title: null, description: null, status: 'TODO'};
    self.tasks = [];
    self.showAlert = false;
    self.filter = 'ALL'

    self.createTask = createTask;
    self.updateTask = updateTask;
    self.deleteTask = deleteTask;
    self.closeAlert = closeAlert;
    self.fetchTasks = fetchTasks;

    fetchTasks();

    function fetchTasks() {
        TaskService.fetchTasks(self.filter)
            .then(
                function (d) {
                    self.tasks = d;
                    self.task.title = null
                    self.task.description = null
                    self.task.status = 'TODO'
                },
                function (errResponse) {
                    console.error('Error while fetching Tasks');
                }
            );
    }

    function createTask(){
        if (self.task.title === "" || self.task.title === null) {
            self.showAlert = true;
            return;
        }
        TaskService.createTask(self.task)
            .then(
                fetchTasks,
                function(errResponse){
                    console.error('Error while creating Task');
                }
            );
    }

    function deleteTask(taskId){
        TaskService.updateTask(taskId, 'DELETED')
            .then(
                function(response){
                    self.tasks = self.tasks.filter(function(item) {
                        return item.id !== taskId
                    })
                },
                function(errResponse){
                    console.error('Error while deleting Task');
                }
            );
    }

    function updateTask(taskId, status){
        console.log(status)
        TaskService.updateTask(taskId, status)
            .then(
                function(response){
                    console.log('Updated Task');
                },
                function(errResponse){
                    console.error('Error while updating Task');
                }
            );
    }

    function closeAlert() {
        self.showAlert = false;
    }
}]);