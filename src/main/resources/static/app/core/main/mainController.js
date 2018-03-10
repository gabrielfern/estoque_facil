app.controller("SearchProductCtrl", function ($scope, $uibModal, $http, toastr,$location, mainService) {

    var urlServer = "/api";
    // $scope.title = "Search Product";
    $scope.productsList = [];
    $scope.produtos = [];
    $scope.admin = localStorage.getItem('senha') === 'banana';

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
            ariaDescribedBy: 'Formulário para adição de um novo produto',
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
            ariaDescribedBy: 'Formulário para Atribuir preço á Produto',
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

    $scope.$on('login:updated', function(event) {
        $scope.admin = localStorage.getItem('senha') === 'banana';
    });

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

app.controller("CriarLoteCtrl", function ($scope, $uibModalInstance, $http, toastr, produto, $rootScope) {

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
            dataDeValidade: {
                dia: dataDeValidade.getDate(),
                mes: dataDeValidade.getMonth() + 1,
                ano: dataDeValidade.getFullYear()
            },
            numeroDeItens: numeroDeItens
        }

        $http.post("/api/admin/produtos/" + produto.id + "/lotes", JSON.stringify(lote))
            .then(function success(response) {
                console.log(response)
                if (response.status === 201) {
                    console.log("Lote criado com sucesso!");
                    toastr.success("Lote criado com sucesso!");
                    $rootScope.$broadcast('lotes:updated');
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

app.controller("CriarSaleCtrl", function($scope, mainService, toastr, $uibModalInstance, $rootScope) {

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
                $rootScope.$broadcast('vendas:updated');
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

        $uibModalInstance.close({
            status: 201
        });
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

});

app.controller("navbarController", function($scope, $uibModal, mainService, toastr) {

    $scope.admin = localStorage.getItem("senha") === 'banana';
    $scope.logged = localStorage.getItem("logado") !== 'false';

    $scope.openCriarVendaDialog = function() {

        let modalInstance = $uibModal.open({
                ariaLabelledBy: 'Registrar venda',
                ariaDescribedBy: 'Registre uma venda.',
                templateUrl: 'app/core/main/createSaleView.html',
                controller: 'CriarSaleCtrl'
            }
        );

    };

    $scope.$on('vendas:updated', function(event) {
        mainService.getProdutosFalta().then((response) => {
            const itensFalta = response.data;

            for(let i = 0; i < itensFalta.length; i ++) {
                toastr.info(itensFalta[i].nome + ' com apenas ' + itensFalta[i].qtdProdutosDisponiveis + ' quantidade(s) disponíveis no estoque.', 'Produto em falta');
            }
        });
    });

    $scope.$on('lotes:updated', function(event) {
        mainService.getProdutosFalta().then((response) => {
            const itensFalta = response.data;

            for(let i = 0; i < itensFalta.length; i ++) {
                toastr.info(itensFalta[i].nome + ' com apenas ' + itensFalta[i].qtdProdutosDisponiveis + ' quantidade(s) disponíveis no estoque.', 'Produto em falta');
            }

            mainService.getProdutosPertoVencimento().then((response) => {
                const itensFalta = response.data;

                for(let i = 0; i < itensFalta.length; i ++) {
                    toastr.warning(itensFalta[i].nome + ' com lote perto do vencimento abaixo de 30 dias.', 'Produto perto da Validade');
                }
            })
        });


    });

    $scope.openLoginDialog = function() {
        const modalInstance = $uibModal.open({
            ariaLabelledBy: 'Realize o login',
            ariaDescribedBy: 'Fazer login.',
            templateUrl: 'app/core/main/loginView.html',
            controller: 'loginCtrl'
        });

        modalInstance.result.then(function (result) {
            if (result.status === 201) {
                $scope.admin = localStorage.getItem("senha") === 'banana';
                $scope.logged = true;
            }
        });
    };

    $scope.logout = function() {
        localStorage.removeItem('senha');
        localStorage.setItem('logado', 'false');
        $scope.logged = false;
        $scope.admin = false;
        $location.path('/');
    }
});

app.controller("detalhesVendaCtrl", function($scope, sale, $uibModalInstance) {
    $scope.venda = sale;

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});

app.controller("loginCtrl", function($scope, toastr, $uibModalInstance, mainService, $rootScope) {
    
    $scope.logar = function(usuario) {
      mainService.authenticate(angular.copy(usuario)).then(async () => {
          await localStorage.setItem("senha", usuario.senha);
          await localStorage.setItem('logado', 'true');
          toastr.success('Login realizado com sucesso!');
          $rootScope.$broadcast('login:updated');
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

app.controller('NotificationsCtrl', function($scope, toastr, mainService, $interval) {

    $scope.produtosEmFalta = [];
    $scope.produtosPertoVencimento = [];

    const carregaProdutosEmFalta = function() {
        mainService.getProdutosFalta().then((response) => {
            $scope.produtosEmFalta = response.data;
        });
    };

    const carregaPertoVencimento = function() {
      mainService.getProdutosPertoVencimento().then((response) => {
          $scope.produtosPertoVencimento = response.data;
      });
    };

    $scope.$on('vendas:updated', function(event) {
        carregaProdutosEmFalta();
    });

    $scope.$on('lotes:updated', function(event) {
        carregaProdutosEmFalta();
        carregaPertoVencimento();
    });

    $interval(function() {
        carregaPertoVencimento();
        console.log('executou');
    }, 60000);

    carregaProdutosEmFalta();
    carregaPertoVencimento();
});

app.controller('RelatorioGeralCtrl', function($scope, mainService, $uibModal) {

    $scope.registrosVenda = [];
    $scope.produtos = [];

    const loadData = function() {
        mainService.getAllRegistrosVenda().then(function(response) {
            $scope.registrosVenda = response.data;
            mainService.getAllProducts().then(function(response) {
                $scope.produtos = response.data;
            });
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
    };

    $scope.openDetalhesProduto = function(produto) {
        $uibModal.open({
            ariaLabelledBy: 'Detalhes Produto',
            ariaDescribedBy: 'Detalhes Produto.',
            templateUrl: 'app/core/main/detailsProductView.html',
            controller: 'detalhesProdutoCtrl',
            resolve: {
                product: function() {
                    return angular.copy(produto);
                }
            }
        })
    };

    $scope.ordenarRegistroPor = function(campo) {
        $scope.criterioDeOrdenacaoRegistro = campo;
        $scope.direcaoDaOrdenacaoRegistro = !$scope.direcaoDaOrdenacaoRegistro;
    };

    $scope.ordenarProdutoPor = function(campo) {
        $scope.criterioDeOrdenacaoProduto = campo;
        $scope.direcaoDaOrdenacaoProduto = !$scope.direcaoDaOrdenacaoProduto;
    };

    $scope.$on('vendas:updated', function(event) {
        loadData();
    });

    loadData();
});

app.controller("detalhesProdutoCtrl", function($scope, product, $uibModalInstance) {
    $scope.produto = product;
    $scope.lotes = product.lotes;
    $scope.qtdLotes = $scope.lotes.length;

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    $scope.ordenarPor = function(campo) {
        $scope.criterioDeOrdenacaoLote = campo;
        $scope.direcaoDaOrdenacaoLote = !$scope.direcaoDaOrdenacaoLote;
    };
});


app.controller("CategoriasCtrl", function($scope, mainService) {
    $scope.categorias = [];

    mainService.getCategorias().then((response) => {
        $scope.categorias = response.data;

        $scope.$watch('categorias', function(depois, antes) {
        	for (let i = 0; i < depois.length; i++)
        		if (depois[i].desconto !== antes[i].desconto)
        			mainService.putCategoria(depois[i].id, JSON.stringify(depois[i]))
        }, true)
    })
});