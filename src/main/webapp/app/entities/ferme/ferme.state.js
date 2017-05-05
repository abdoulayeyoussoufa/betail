(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ferme', {
            parent: 'app',
            url: '/ferme',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.ferme.home.title'
            },
           
                    templateUrl: 'app/entities/ferme/fermes.html',
                    controller: 'FermeController',
                    controllerAs: 'vm',
 
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ferme');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ferme-detail', {
            parent: 'ferme',
            url: '/ferme/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.ferme.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ferme/ferme-detail.html',
                    controller: 'FermeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ferme');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ferme', function($stateParams, Ferme) {
                    return Ferme.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ferme',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ferme-detail.edit', {
            parent: 'ferme-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ferme/ferme-dialog.html',
                    controller: 'FermeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ferme', function(Ferme) {
                            return Ferme.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ferme.new', {
            parent: 'ferme',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ferme/ferme-dialog.html',
                    controller: 'FermeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                superficie: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ferme', null, { reload: 'ferme' });
                }, function() {
                    $state.go('ferme');
                });
            }]
        })
        .state('ferme.edit', {
            parent: 'ferme',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ferme/ferme-dialog.html',
                    controller: 'FermeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ferme', function(Ferme) {
                            return Ferme.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ferme', null, { reload: 'ferme' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ferme.delete', {
            parent: 'ferme',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ferme/ferme-delete-dialog.html',
                    controller: 'FermeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ferme', function(Ferme) {
                            return Ferme.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ferme', null, { reload: 'ferme' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
