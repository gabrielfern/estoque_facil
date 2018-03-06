app.controller("SearchProductCtrl", function ($scope, $uibModal, $http, toastr,$location, mainService) {

    var urlServer = "/api";
    // $scope.title = "Search Product";
    $scope.productsList = [];
    $scope.produtos = [];

    var loadProductsList = function () {
        // $http.get("http://localhost:8080/api/")
        //     .then(function successCallback(response) {
        //         $scope.productsList = response.data;
        //     });

        mainService.getAllProducts()
            .then(function successCallback(response) {
                $scope.productsList = response.data;
            }, function errorCallback(error) {
                console.log(error);
            });
    };

    $scope.openCreateProductDialog = function() {
        var modalInstance = $uibModal.open({
            ariaLabelledBy: 'Adicionar Produto',
            ariaDescribedBy: 'Formulario para adição de um novo produto',
            templateUrl: 'app/core/main/createProductView.html',
            controller: 'CreateProductCtrl',
            controllerAs: 'cpCtrl'
        });

        modalInstance.result.then(function (result) {
            if (result === 201) {
              loadProductsList();
            }
        });
    };

    $scope.openAtribuirPrecoParaProdutoDialog = function(product) {

        // var modalInstance = $uibModal.open({
        //     ariaLabelledBy: 'Adicionar Produto',
        //     ariaDescribedBy: 'Formulario para adição de um novo produto',
        //     templateUrl: 'core/main/create-product.html',
        //     controller: 'CreateProductCtrl'
        // });

        var modalInstance = $uibModal.open({
            ariaLabelledBy: 'Atribuir preço á Produto',
            ariaDescribedBy: 'Formulario para Atribuir preço á Produto',
            templateUrl: 'app/core/main/updateProductPriceView.html',
            controller: 'UpdateProductPriceCtrl',
            resolve: {
                produto: function () {
                    return angular.copy(product);
                }
            }
        });

        modalInstance.result.then(function (result) {
            console.log(result)
            if (result.status === 200) {
                loadProductsList();
            }
        });
    };

    $scope.pesquisarProdutoPorId = function(id) {
        // implementar
        $http.get("/api/produtos" + id)
            .then(function successCallback(response) {
                $scope.productsList = [
                    response.data
                ]
                console.error("Não carregou")
            }, function errorCallback(error) {
                console.error(error);
                if (error.status === 404) {
                    console.log(error);
                    console.log(error.errorMessage);
                    toastr.error(error.data.errorMessage);
                } else if (error.status === 400) {
                    toastr.error("Produto não encontrado");
                }
            });
    };

    $scope.openCriarLoteDialog = function(product) {

        var modalInstance = $uibModal.open({
            ariaLabelledBy: 'Criar lote',
            ariaDescribedBy: 'Formulario para criar lote',
            templateUrl: 'app/core/main/createLoteView.html',
            controller: 'CriarLoteCtrl',
            resolve: {
                produto: function () {
                    return angular.copy(product);
                }
            }
        });

        modalInstance.result.then(function (result) {
            console.log(result)
            if (result.status === 201) {
                loadProductsList();
            }
        });
    };

    // $scope.createLot = function(produto) {
    //     console.log(produto)
    // };
    //
    // $scope.atribuirPrice = function(product) {
    //     console.log(product)
    // };

    loadProductsList();
});

app.controller("CreateProductCtrl", function ($uibModalInstance, $http, toastr) {

    var vm = this;

    vm.product = {};

    vm.listaDeSituacoes = [
        {
            nome: "Disponivel",
            valor: 1
        }, {
            nome: "Em Falta",
            valor: 2
        }
    ];

    this.createProduct = function (product) {

        // if (situacao) {
        //     if (situacao === 1) {
        //         product.situacao = 1
        //     } else {
        //         product.situacao = 2
        //     }
        // }

        // product.situacao = situacao === 1 ? 1 : 2;

        $http.post("/api/admin/produtos", JSON.stringify(product))
            .then(function success(response) {
                if (response.status === 201) {
                    toastr.success("Produto adicionado com sucesso!");
                    vm.product = {};
                    console.log(response)
                    $uibModalInstance.close(201);
                }
            }, function error(error) {
                console.log(error);
                toastr.error("Problemas ao tentar adicionar produto.");
            });
    };

    this.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

app.controller("UpdateProductPriceCtrl", function ($scope, $uibModalInstance, mainService, toastr, produto) {

    $scope.produto = produto;

    $scope.submit = function (product) {

        //adicionar

        console.log(product)

        mainService.updateProductById(product.id, product)
            .then(function success(response) {

                if (response.status === 200) {
                    toastr.success("Produto editado com sucesso!");
                    $uibModalInstance.close({
                        status: 200,
                        newProduct: response.data
                    });
                }
            }, function error(error) {
                console.log(error);
                toastr.error("Problemas ao tentar atribuir preço ao produto: " + product.id);
            });

    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

app.controller("CriarLoteCtrl", function ($scope, $uibModalInstance, $http, toastr, produto) {

    $scope.produto = produto;
    $scope.dateformat = 'dd/MM/yyyy';
    $scope.datePicker = {
        opened : false
    };

    $scope.dataDeValidade = new Date();
    $scope.numeroDeItens = 0;

    $scope.dateOptions = {
        formatYear: 'yy',
        minDate: new Date(),
        startingDay: 1
    };

    $scope.submit = function (dataDeValidade, numeroDeItens) {

        //adicionar
        var lote = {
            dataDeValidade: dataDeValidade.getDay() + "/" + (dataDeValidade.getMonth() + 1) + dataDeValidade.getFullYear(),
            numeroDeItens: numeroDeItens
        }

        $http.post("/api/admin/produtos/" + produto.id + "/lotes", JSON.stringify(lote))
            .then(function success(response) {
                console.log(response)
                if (response.status === 201) {
                    console.log("Lote criado com sucesso!");
                    toastr.success("Lote criado com sucesso!");
                    $uibModalInstance.close({
                        status: 201
                    });
                }
            }, function error(error) {
                console.log(error);
                toastr.error("Problemas ao tentar adicionar produto.");
            });
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    $scope.openDatePicker = function () {
        $scope.datePicker.opened = true;
    }
});

app.controller("CriarSaleCtrl", function($scope, mainService, toastr, $uibModalInstance) {

    $scope.productsList = [];

    mainService.getAllProducts().then( function(response) {
        $scope.productsList = response.data;
    });

    $scope.verificarProdutoSelecionado = function(produtos) {
        $scope.temProdutoSelecionado = produtos.some(function(produto) {
            return produto.selecionado;
        })
    };

    $scope.registraVenda = function(produtos) {
        const produtosAVender = produtos.filter(function(produto) {
            if(produto.selecionado) return produto;
        });

        const itensVenda = [];

        for(let i = 0; i < produtosAVender.length; i ++) {
            itensVenda.push({"produto": produtosAVender[i], "qtd": produtosAVender[i].qtdVenda});
        }

        const venda = { "itens": itensVenda, dataVenda: new Date() };

        mainService.registerSale(venda).then(function(response) {
            $scope.verificarProdutoSelecionado($scope.productsList);
            toastr.success('Venda registrada com sucesso!');
            mainService.getAllProducts().then(function(response) {
                $scope.productsList = response.data;
                $uibModalInstance.close({
                    status: 201
                });
            })
        }).catch(function(error) {
            $scope.verificarProdutoSelecionado($scope.productsList);
            toastr.error('Problemas ao registrar a venda.');
        })
    };

    $scope.cancelarVenda = function(produtos) {
        const produtosAVender = produtos.filter(function(produto) {
            if(produto.selecionado) return produto;
        });

        for(let i = 0; i < produtosAVender.length; i ++)
            produtosAVender[i].selecionado = false;

        toastr.success('Venda cancelada com sucesso!');

        $scope.cancel();
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

});

app.controller('SalesCtrl', function($scope, mainService, $uibModal) {

    $scope.registrosVenda = [];

    const loadRegistroVendaList = function() {
        mainService.getAllRegistrosVenda().then(function(response) {
            $scope.registrosVenda = response.data;
        });
    };

    $scope.openDetalhesVenda = function(venda) {
        let modalInstance = $uibModal.open({
                ariaLabelledBy: 'Detalhes Venda',
                ariaDescribedBy: 'Detalhes Venda.',
                templateUrl: 'app/core/main/detailsSaleView.html',
                controller: 'detalhesVendaCtrl',
                resolve: {
                    sale: function() {
                        return angular.copy(venda);
                    }
                }
            }
        );
    }

    loadRegistroVendaList();
});

app.controller("navbarController", function($scope, $uibModal) {

    $scope.admin = localStorage.getItem("senha") === 'banana';

    $scope.openCriarVendaDialog = function() {

        let modalInstance = $uibModal.open({
                ariaLabelledBy: 'Registrar venda',
                ariaDescribedBy: 'Registre uma venda.',
                templateUrl: 'app/core/main/createSaleView.html',
                controller: 'CriarSaleCtrl'
            }
        );

    };

    $scope.openLoginDialog = function() {
        const modalInstance = $uibModal.open({
            ariaLabelledBy: 'Realize o login',
            ariaDescribedBy: 'Fazer login.',
            templateUrl: 'app/core/main/loginView.html',
            controller: 'loginCtrl'
        });

        modalInstance.result.then(function (result) {
            if (result.status === 201) {
                $scope.admin = true;
                $scope.logged = true;
            }
        });
    };

    $scope.logout = function() {
        localStorage.removeItem('senha');
        $scope.logged = false;
        $scope.admin = false;
    }
});

app.controller("detalhesVendaCtrl", function($scope, sale, $uibModalInstance) {
    $scope.venda = sale;

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

app.controller("loginCtrl", function($scope, toastr, $uibModalInstance, mainService) {

    $scope.logar = function(usuario) {
      mainService.authenticate(angular.copy(usuario)).then(async () => {
          await localStorage.setItem("senha", usuario.senha);
          toastr.success('Login realizado com sucesso!');
          $uibModalInstance.close({
              status: 201
          });
      }).catch(() => {
          toastr.error('Não foi possivel realizar o login. Cheque suas credenciais');
          delete $scope.usuario;
          $scope.loginForm.$setPristine();
      })
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});