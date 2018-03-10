app.config(function ($routeProvider) {
    $routeProvider.when("/",{
      templateUrl: "app/core/main/searchProductView.html",
      controller: "SearchProductCtrl"
    }).when("/products",{
        templateUrl: "app/core/main/searchProductView.html",
        controller: "SearchProductCtrl"
    }).when("/relatorio", {
        templateUrl: "app/core/main/salesView.html",
        controller: "SalesCtrl"
    }).when("/notificacoes", {
        templateUrl: "app/core/main/notificationsView.html",
        controller: "NotificationsCtrl"
    })
    //     .when("/products/create-product",{
    //     templateUrl: "view/createProductView.html",
    //     controller: "CreateProductCtrl"
    // })
        .otherwise({
        redirectTo: '/'
    });
});
