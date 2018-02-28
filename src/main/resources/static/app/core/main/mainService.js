app.factory("mainService", function ($http,BASE_SERVER_URL) {


    return {
        getAllProducts: _getAllProducts,
        updateProductById: _updateProductById
    };

    function _getAllProducts() {
        return $http.get(BASE_SERVER_URL + "/produtos")
    }

    function _updateProductById(id, data) {
        return $http.put(BASE_SERVER_URL + "/admin/produtos/" + id, data)
    }
});
