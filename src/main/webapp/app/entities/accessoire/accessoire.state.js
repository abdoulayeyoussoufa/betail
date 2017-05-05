(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('accessoire', {
            parent: 'app',
            url: '/accessoire',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.accessoire.home.title'
            },
           
                    templateUrl: 'app/entities/accessoire/accessoires.html',
                    controller: 'AccessoireController',
                    controllerAs: 'vm',
            
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('accessoire');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('accessoire-detail', {
            parent: 'accessoire',
            url: '/accessoire/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.accessoire.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/accessoire/accessoire-detail.html',
                    controller: 'AccessoireDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('accessoire');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Accessoire', function($stateParams, Accessoire) {
                    return Accessoire.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'accessoire',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('accessoire-detail.edit', {
            parent: 'accessoire-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/accessoire/accessoire-dialog.html',
                    controller: 'AccessoireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Accessoire', function(Accessoire) {
                            return Accessoire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('accessoire.new', {
            parent: 'accessoire',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/accessoire/accessoire-dialog.html',
                    controller: 'AccessoireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                type: null,
                                couleur: null,
                                prix: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('accessoire', null, { reload: 'accessoire' });
                }, function() {
                    $state.go('accessoire');
                });
            }]
        })
        .state('accessoire.edit', {
            parent: 'accessoire',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/accessoire/accessoire-dialog.html',
                    controller: 'AccessoireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Accessoire', function(Accessoire) {
                            return Accessoire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('accessoire', null, { reload: 'accessoire' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('accessoire.delete', {
            parent: 'accessoire',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/accessoire/accessoire-delete-dialog.html',
                    controller: 'AccessoireDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Accessoire', function(Accessoire) {
                            return Accessoire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('accessoire', null, { reload: 'accessoire' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
