(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('carnet-sante', {
            parent: 'app',
            url: '/carnet-sante',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.carnetSante.home.title'
            },
            
                    templateUrl: 'app/entities/carnet-sante/carnet-santes.html',
                    controller: 'CarnetSanteController',

            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carnetSante');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('carnet-sante-detail', {
            parent: 'carnet-sante',
            url: '/carnet-sante/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.carnetSante.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carnet-sante/carnet-sante-detail.html',
                    controller: 'CarnetSanteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carnetSante');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CarnetSante', function($stateParams, CarnetSante) {
                    return CarnetSante.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'carnet-sante',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('carnet-sante-detail.edit', {
            parent: 'carnet-sante-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carnet-sante/carnet-sante-dialog.html',
                    controller: 'CarnetSanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarnetSante', function(CarnetSante) {
                            return CarnetSante.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carnet-sante.new', {
            parent: 'carnet-sante',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carnet-sante/carnet-sante-dialog.html',
                    controller: 'CarnetSanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                datVaccination: null,
                                nom: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('carnet-sante', null, { reload: 'carnet-sante' });
                }, function() {
                    $state.go('carnet-sante');
                });
            }]
        })
        .state('carnet-sante.edit', {
            parent: 'carnet-sante',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carnet-sante/carnet-sante-dialog.html',
                    controller: 'CarnetSanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarnetSante', function(CarnetSante) {
                            return CarnetSante.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carnet-sante', null, { reload: 'carnet-sante' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carnet-sante.delete', {
            parent: 'carnet-sante',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carnet-sante/carnet-sante-delete-dialog.html',
                    controller: 'CarnetSanteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CarnetSante', function(CarnetSante) {
                            return CarnetSante.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carnet-sante', null, { reload: 'carnet-sante' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
