var page = angular.module('pageModule',[]);

page.controller('homeController',['$scope', function(c){
    c.message = 'welcome to collaboration. I am in home page.'
}]);

page.controller('contactController',['$scope', function(c){
    c.message = 'I am in contact page.'
}]);

page.controller('aboutController',['$scope', function(c){
    c.message = 'I am in about page.'
}]);