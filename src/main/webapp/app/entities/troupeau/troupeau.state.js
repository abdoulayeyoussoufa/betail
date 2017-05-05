(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('troupeau', {
            parent: 'app',
            url: '/troupeau',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.troupeau.home.title'
            },
           
                    templateUrl: 'app/entities/troupeau/troupeaus.html',
                    controller: 'TroupeauController',
                    controllerAs: 'vm',
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('troupeau');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('troupeau-detail', {
            parent: 'troupeau',
            url: '/troupeau/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.troupeau.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/troupeau/troupeau-detail.html',
                    controller: 'TroupeauDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('troupeau');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Troupeau', function($stateParams, Troupeau) {
                    return Troupeau.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'troupeau',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('troupeau-detail.edit', {
            parent: 'troupeau-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/troupeau/troupeau-dialog.html',
                    controller: 'TroupeauDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Troupeau', function(Troupeau) {
                            return Troupeau.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('troupeau.new', {
            parent: 'troupeau',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/troupeau/troupeau-dialog.html',
                    controller: 'TroupeauDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                categorie: null,
                                effectif: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('troupeau', null, { reload: 'troupeau' });
                }, function() {
                    $state.go('troupeau');
                });
            }]
        })
        .state('troupeau.edit', {
            parent: 'troupeau',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/troupeau/troupeau-dialog.html',
                    controller: 'TroupeauDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Troupeau', function(Troupeau) {
                            return Troupeau.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('troupeau', null, { reload: 'troupeau' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('troupeau.delete', {
            parent: 'troupeau',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/troupeau/troupeau-delete-dialog.html',
                    controller: 'TroupeauDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Troupeau', function(Troupeau) {
                            return Troupeau.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('troupeau', null, { reload: 'troupeau' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
