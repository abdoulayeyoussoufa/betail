(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('avis-perte', {
            parent: 'app',
            url: '/avis-perte',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.avisPerte.home.title'
            },
           
                    templateUrl: 'app/entities/avis-perte/avis-pertes.html',
                    controller: 'AvisPerteController',
  
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('avisPerte');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('avis-perte-detail', {
            parent: 'avis-perte',
            url: '/avis-perte/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.avisPerte.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/avis-perte/avis-perte-detail.html',
                    controller: 'AvisPerteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('avisPerte');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AvisPerte', function($stateParams, AvisPerte) {
                    return AvisPerte.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'avis-perte',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('avis-perte-detail.edit', {
            parent: 'avis-perte-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avis-perte/avis-perte-dialog.html',
                    controller: 'AvisPerteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AvisPerte', function(AvisPerte) {
                            return AvisPerte.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('avis-perte.new', {
            parent: 'avis-perte',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avis-perte/avis-perte-dialog.html',
                    controller: 'AvisPerteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                animalId: null,
                                nomAnimalper: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('avis-perte', null, { reload: 'avis-perte' });
                }, function() {
                    $state.go('avis-perte');
                });
            }]
        })
        .state('avis-perte.edit', {
            parent: 'avis-perte',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avis-perte/avis-perte-dialog.html',
                    controller: 'AvisPerteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AvisPerte', function(AvisPerte) {
                            return AvisPerte.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('avis-perte', null, { reload: 'avis-perte' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('avis-perte.delete', {
            parent: 'avis-perte',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/avis-perte/avis-perte-delete-dialog.html',
                    controller: 'AvisPerteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AvisPerte', function(AvisPerte) {
                            return AvisPerte.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('avis-perte', null, { reload: 'avis-perte' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
