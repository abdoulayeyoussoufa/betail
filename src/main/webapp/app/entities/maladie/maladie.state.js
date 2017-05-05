(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('maladie', {
            parent: 'app',
            url: '/maladie',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.maladie.home.title'
            },
            
                    templateUrl: 'app/entities/maladie/maladies.html',
                    controller: 'MaladieController',
                    controllerAs: 'vm',
           
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('maladie');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('maladie-detail', {
            parent: 'maladie',
            url: '/maladie/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.maladie.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/maladie/maladie-detail.html',
                    controller: 'MaladieDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('maladie');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Maladie', function($stateParams, Maladie) {
                    return Maladie.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'maladie',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('maladie-detail.edit', {
            parent: 'maladie-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/maladie/maladie-dialog.html',
                    controller: 'MaladieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Maladie', function(Maladie) {
                            return Maladie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('maladie.new', {
            parent: 'maladie',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/maladie/maladie-dialog.html',
                    controller: 'MaladieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                type: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('maladie', null, { reload: 'maladie' });
                }, function() {
                    $state.go('maladie');
                });
            }]
        })
        .state('maladie.edit', {
            parent: 'maladie',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/maladie/maladie-dialog.html',
                    controller: 'MaladieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Maladie', function(Maladie) {
                            return Maladie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('maladie', null, { reload: 'maladie' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('maladie.delete', {
            parent: 'maladie',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/maladie/maladie-delete-dialog.html',
                    controller: 'MaladieDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Maladie', function(Maladie) {
                            return Maladie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('maladie', null, { reload: 'maladie' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
