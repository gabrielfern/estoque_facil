app.config(function ($routeProvider) {
    $routeProvider.when("/",{
      templateUrl: "app/core/main/searchProductView.html",
      controller: "SearchProductCtrl"
    }).when("/products",{
        templateUrl: "app/core/main/searchProductView.html",
        controller: "SearchProductCtrl"
    }).when("/relatorio", {
        templateUrl: "app/core/main/relatorioView.html",
        controller: "RelatorioGeralCtrl"
    }).when("/notificacoes", {
        templateUrl: "app/core/main/notificationsView.html",
        controller: "NotificationsCtrl"
    }).when("/categorias", {
        templateUrl: "app/core/main/categoriasView.html",
        controller: "CategoriasCtrl"
    }).when("/reservas", {
        templateUrl: "app/core/main/reservasView.html",
        controller: "ReservasCtrl"
    }).otherwise({
        redirectTo: '/'
    });
});
