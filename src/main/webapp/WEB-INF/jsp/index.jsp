<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>TODO App</title>
    <base href="/" />
    <link rel="stylesheet" href="css/style.css" />
<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
    <script src="js/app.js"></script>
    <script src="js/service/task_service.js"></script>
    <script src="js/controller/task_controller.js"></script>
</head>
<body ng-app="myApp" class="ng-cloak">
    <div ng-controller="TaskController as ctrl" class="container">
        <div class="alert alert-danger" role="alert" ng-show="ctrl.showAlert">
            <span class="closebtn" ng-click="ctrl.closeAlert()">&times;</span>
            Title cannot be empty.
        </div>
        <div class="row">
            <div class="app-container col" style="width: 18rem;">
                <h1 class="app-header">ADD TODO TASK</h1>
                <form>
                    <div class="add-task">
                        <div class="form-group">
                            <label for="taskTitle" class="form-label">Title</label>
                            <input type="text" autocomplete="off" class="task-input" id="taskTitle" aria-describedby="titleHelp" ng-model="ctrl.task.title" (keyup.enter)="ctrl.createTask()" placeholder="Enter Task Title">
                            <small id="titleHelp" class="form-text">*Required</small>
                        </div>
                        <div class="form-group">
                            <label for="taskDescription" class="form-label">Description</label>
                            <textarea type="text" class="task-input" id="taskDescription" placeholder="Add Description (optional)" rows="3" ng-model="ctrl.task.description" (keyup.enter)="ctrl.createTask()"></textarea>
                        </div>
                        <div class="form-check">
                            <input type="radio" class="btn-check" ng-checked="ctrl.task.status" ng-model="ctrl.task.status" ng-value="'TODO'" name="task-status" id="todo-outlined" autocomplete="off">
                            <label class="btn btn-outline-secondary" for="todo-outlined">ToDo</label>

                            <input type="radio" class="btn-check" ng-checked="ctrl.task.status" ng-model="ctrl.task.status" ng-value="'IN_PROGRESS'" name="task-status" id="inprogress-outlined" autocomplete="off" >
                            <label class="btn btn-outline-primary" for="inprogress-outlined">In-progress</label>

                            <input type="radio" class="btn-check" ng-checked="ctrl.task.status" ng-model="ctrl.task.status" ng-value="'DONE'" name="task-status" id="done-outlined" autocomplete="off" >
                            <label class="btn btn-outline-success" for="done-outlined">Done</label>
                        </div>
                        <input type="button" value="" class="submit-task" ng-click="ctrl.createTask()" title="Add Task">

                    </div>
                </form>
            </div>
            <div class="app-container col" id="taskList" >
                <h1 class="app-header">TODO LIST</h1>
                <div class="btn-group flex-wrap" role="group">
                    <input type="radio" class="btn-check" ng-checked="ctrl.filter" ng-model="ctrl.filter" ng-value="'ALL'" ng-change="ctrl.fetchTasks()" name="taskFilter" id="all" autocomplete="off">
                    <label class="btn btn-outline-info" for="all">All</label>

                    <input type="radio" class="btn-check" ng-checked="ctrl.filter" ng-model="ctrl.filter" ng-value="'TODO'" ng-change="ctrl.fetchTasks()" name="taskFilter" id="todo" autocomplete="off" >
                    <label class="btn btn-outline-secondary" for="todo">ToDo</label>

                    <input type="radio" class="btn-check" ng-checked="ctrl.filter" ng-model="ctrl.filter" ng-value="'IN_PROGRESS'" ng-change="ctrl.fetchTasks()" name="taskFilter" id="inprogress" autocomplete="off">
                    <label class="btn btn-outline-primary" for="inprogress">In-progress</label>

                    <input type="radio" class="btn-check" ng-checked="ctrl.filter" ng-model="ctrl.filter" ng-value="'DONE'" ng-change="ctrl.fetchTasks()" name="taskFilter" id="done" autocomplete="off">
                    <label class="btn btn-outline-success" for="done">Done</label>
                </div>
                <ul class="task-list">
                    <li class="task-list-item" ng-repeat="task in ctrl.tasks">
                        <label class="task-list-item-label">
                            <span ng-bind="task.title"></span>
                        </label>
                        <div class="btn-group flex-wrap btn-group-sm" role="group">
                            <input type="radio" class="btn-check" ng-checked="task.status" ng-model="task.status" ng-value="'TODO'" ng-change="ctrl.updateTask(task.id, 'TODO')" name="taskStatus{{task.id}}" id="todo{{task.id}}" autocomplete="off" >
                            <label class="btn btn-outline-secondary btn-sm" for="todo{{task.id}}">ToDo</label>

                            <input type="radio" class="btn-check" ng-checked="task.status" ng-model="task.status" ng-value="'IN_PROGRESS'" ng-change="ctrl.updateTask(task.id, 'IN_PROGRESS')" name="taskStatus{{task.id}}" id="inprogress{{task.id}}" autocomplete="off">
                            <label class="btn btn-outline-primary btn-sm" for="inprogress{{task.id}}">In-progress</label>

                            <input type="radio" class="btn-check" ng-checked="task.status" ng-model="task.status" ng-value="'DONE'" ng-change="ctrl.updateTask(task.id, 'DONE')" name="taskStatus{{task.id}}" id="done{{task.id}}" autocomplete="off">
                            <label class="btn btn-outline-success btn-sm" for="done{{task.id}}">Done</label>
                        </div>
                        <span type="button" ng-click="ctrl.deleteTask(task.id)" class="delete-btn" title="Delete Task">{{task.del}}</span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</body>

</html>