(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('partenaire-bancaire', {
            parent: 'app',
            url: '/partenaire-bancaire',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.partenaireBancaire.home.title'
            },
           
                    templateUrl: 'app/entities/partenaire-bancaire/partenaire-bancaires.html',
                    controller: 'PartenaireBancaireController',
             
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('partenaireBancaire');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('partenaire-bancaire-detail', {
            parent: 'partenaire-bancaire',
            url: '/partenaire-bancaire/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.partenaireBancaire.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/partenaire-bancaire/partenaire-bancaire-detail.html',
                    controller: 'PartenaireBancaireDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('partenaireBancaire');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PartenaireBancaire', function($stateParams, PartenaireBancaire) {
                    return PartenaireBancaire.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'partenaire-bancaire',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('partenaire-bancaire-detail.edit', {
            parent: 'partenaire-bancaire-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/partenaire-bancaire/partenaire-bancaire-dialog.html',
                    controller: 'PartenaireBancaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PartenaireBancaire', function(PartenaireBancaire) {
                            return PartenaireBancaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('partenaire-bancaire.new', {
            parent: 'partenaire-bancaire',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/partenaire-bancaire/partenaire-bancaire-dialog.html',
                    controller: 'PartenaireBancaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nomReseau: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('partenaire-bancaire', null, { reload: 'partenaire-bancaire' });
                }, function() {
                    $state.go('partenaire-bancaire');
                });
            }]
        })
        .state('partenaire-bancaire.edit', {
            parent: 'partenaire-bancaire',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/partenaire-bancaire/partenaire-bancaire-dialog.html',
                    controller: 'PartenaireBancaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PartenaireBancaire', function(PartenaireBancaire) {
                            return PartenaireBancaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('partenaire-bancaire', null, { reload: 'partenaire-bancaire' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('partenaire-bancaire.delete', {
            parent: 'partenaire-bancaire',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/partenaire-bancaire/partenaire-bancaire-delete-dialog.html',
                    controller: 'PartenaireBancaireDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PartenaireBancaire', function(PartenaireBancaire) {
                            return PartenaireBancaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('partenaire-bancaire', null, { reload: 'partenaire-bancaire' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
