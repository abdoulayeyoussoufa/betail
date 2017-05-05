(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fiche-tracabilite', {
            parent: 'app',
            url: '/fiche-tracabilite',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.ficheTracabilite.home.title'
            },
            
                    templateUrl: 'app/entities/fiche-tracabilite/fiche-tracabilites.html',
                    controller: 'FicheTracabiliteController',
                    controllerAs: 'vm',
           
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ficheTracabilite');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('fiche-tracabilite-detail', {
            parent: 'fiche-tracabilite',
            url: '/fiche-tracabilite/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.ficheTracabilite.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fiche-tracabilite/fiche-tracabilite-detail.html',
                    controller: 'FicheTracabiliteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ficheTracabilite');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FicheTracabilite', function($stateParams, FicheTracabilite) {
                    return FicheTracabilite.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fiche-tracabilite',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('fiche-tracabilite-detail.edit', {
            parent: 'fiche-tracabilite-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-tracabilite/fiche-tracabilite-dialog.html',
                    controller: 'FicheTracabiliteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FicheTracabilite', function(FicheTracabilite) {
                            return FicheTracabilite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fiche-tracabilite.new', {
            parent: 'fiche-tracabilite',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-tracabilite/fiche-tracabilite-dialog.html',
                    controller: 'FicheTracabiliteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                proprietaireActu: null,
                                dateObt: null,
                                lieuActuel: null,
                                dernierProp: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fiche-tracabilite', null, { reload: 'fiche-tracabilite' });
                }, function() {
                    $state.go('fiche-tracabilite');
                });
            }]
        })
        .state('fiche-tracabilite.edit', {
            parent: 'fiche-tracabilite',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-tracabilite/fiche-tracabilite-dialog.html',
                    controller: 'FicheTracabiliteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FicheTracabilite', function(FicheTracabilite) {
                            return FicheTracabilite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fiche-tracabilite', null, { reload: 'fiche-tracabilite' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fiche-tracabilite.delete', {
            parent: 'fiche-tracabilite',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-tracabilite/fiche-tracabilite-delete-dialog.html',
                    controller: 'FicheTracabiliteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FicheTracabilite', function(FicheTracabilite) {
                            return FicheTracabilite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fiche-tracabilite', null, { reload: 'fiche-tracabilite' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
