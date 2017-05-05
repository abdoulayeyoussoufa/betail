(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('facture', {
            parent: 'app',
            url: '/facture',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.facture.home.title'
            },
            
                    templateUrl: 'app/entities/facture/factures.html',
                    controller: 'FactureController',

            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('facture');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('facture-detail', {
            parent: 'facture',
            url: '/facture/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.facture.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facture/facture-detail.html',
                    controller: 'FactureDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('facture');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Facture', function($stateParams, Facture) {
                    return Facture.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'facture',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('facture-detail.edit', {
            parent: 'facture-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facture/facture-dialog.html',
                    controller: 'FactureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Facture', function(Facture) {
                            return Facture.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facture.new', {
            parent: 'facture',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facture/facture-dialog.html',
                    controller: 'FactureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                datFacture: null,
                                numFacture: null,
                                montant: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('facture', null, { reload: 'facture' });
                }, function() {
                    $state.go('facture');
                });
            }]
        })
        .state('facture.edit', {
            parent: 'facture',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facture/facture-dialog.html',
                    controller: 'FactureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Facture', function(Facture) {
                            return Facture.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facture', null, { reload: 'facture' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facture.delete', {
            parent: 'facture',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facture/facture-delete-dialog.html',
                    controller: 'FactureDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Facture', function(Facture) {
                            return Facture.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facture', null, { reload: 'facture' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
