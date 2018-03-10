angular.module("efApp").factory("tokenInterceptor", function($q, $location) {
    return {
        request: function(config) {
            if(config.url.startsWith('/api/admin')) {
                config.params = config.params || {};
                config.params.senha = localStorage.getItem("senha");
            }
            return config;
        },

        responseError: function(rejection) {
            console.log(rejection.message);
            return $q.reject(rejection);
        }
    };
});
