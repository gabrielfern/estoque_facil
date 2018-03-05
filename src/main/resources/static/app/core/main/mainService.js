app.factory("mainService", function ($http,BASE_SERVER_URL) {

    function _getAllProducts() {
        return $http.get(BASE_SERVER_URL + "/produtos")
    }

    function _updateProductById(id, data) {
        return $http.put(BASE_SERVER_URL + "/admin/produtos/" + id, data)
    }

    const _registerSale = function(data) {
        return $http.post(BASE_SERVER_URL + "/admin/vendas", data);
    };

    const _getAllRegistrosVenda = function() {
        return $http.get(BASE_SERVER_URL + "/admin/vendas")
    };

    return {
        getAllProducts: _getAllProducts,
        updateProductById: _updateProductById,
        registerSale: _registerSale,
        getAllRegistrosVenda: _getAllRegistrosVenda
    };
});
