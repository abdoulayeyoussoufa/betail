(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('police', {
            parent: 'app',
            url: '/police',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.police.home.title'
            },
            
                    templateUrl: 'app/entities/police/police.html',
                    controller: 'PoliceController',
                    controllerAs: 'vm',
 
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('police');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('police-detail', {
            parent: 'police',
            url: '/police/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.police.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/police/police-detail.html',
                    controller: 'PoliceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('police');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Police', function($stateParams, Police) {
                    return Police.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'police',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('police-detail.edit', {
            parent: 'police-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/police/police-dialog.html',
                    controller: 'PoliceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Police', function(Police) {
                            return Police.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('police.new', {
            parent: 'police',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/police/police-dialog.html',
                    controller: 'PoliceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                section: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('police', null, { reload: 'police' });
                }, function() {
                    $state.go('police');
                });
            }]
        })
        .state('police.edit', {
            parent: 'police',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/police/police-dialog.html',
                    controller: 'PoliceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Police', function(Police) {
                            return Police.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('police', null, { reload: 'police' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('police.delete', {
            parent: 'police',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/police/police-delete-dialog.html',
                    controller: 'PoliceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Police', function(Police) {
                            return Police.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('police', null, { reload: 'police' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
