<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>TODO App</title>
    <base href="/" />
    <link rel="stylesheet" href="css/style.css" />

    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
    <script src="js/app.js"></script>
    <script src="js/service/task_service.js"></script>
    <script src="js/controller/task_controller.js"></script>
</head>
<body ng-app="myApp" class="ng-cloak">
<div class="app-container" id="taskList" ng-controller="TaskController as ctrl">
    <h1 class="app-header">TO DO LIST</h1>
    <div class="add-task">
        <input type="text" autocomplete="off" placeholder="Add New Task" v-model="tasks.name" @keyup.enter="newItem" class="task-input">
        <input type="submit" value="" class="submit-task" @click="newItem" title="Add Task">
    </div>
    <ul class="task-list">
        <li class="task-list-item" ng-repeat="task in ctrl.tasks">
            <label class="task-list-item-label">
                <input type="checkbox">
                <span ng-bind="task.title"></span>
            </label>
            <span  class="delete-btn" title="Delete Task">{{task.del}}</span>
        </li>
    </ul>
</div>
</body>

</html>