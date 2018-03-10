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

    const _authenticate = function(data) {
        return $http.post(BASE_SERVER_URL + "/autentica", data);
    };

    const _getProdutosFalta = function() {
        return $http.get(BASE_SERVER_URL + "/admin/produtos/falta");
    };

    const _getProdutosPertoVencimento = function() {
        return $http.get(BASE_SERVER_URL + '/admin/produtos/vencidos');
    };
    
    const _getCategorias = function() {
        return $http.get(BASE_SERVER_URL + '/admin/categorias');
    };
    
    const _putCategoria = function(id, data) {
        return $http.put(BASE_SERVER_URL + '/admin/categorias/' + id, data);
    };

    return {
        getAllProducts: _getAllProducts,
        updateProductById: _updateProductById,
        registerSale: _registerSale,
        getAllRegistrosVenda: _getAllRegistrosVenda,
        authenticate: _authenticate,
        getProdutosFalta: _getProdutosFalta,
        getProdutosPertoVencimento: _getProdutosPertoVencimento,
        getCategorias: _getCategorias,
        putCategoria: _putCategoria
    };
});
