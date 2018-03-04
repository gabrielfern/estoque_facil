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
            return $q.reject(rejection);
        }
    };
});
