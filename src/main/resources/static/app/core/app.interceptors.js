angular.module("efApp").config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push("tokenInterceptor");
}]);