(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('epidemie', {
            parent: 'app',
            url: '/epidemie',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.epidemie.home.title'
            },
            
                    templateUrl: 'app/entities/epidemie/epidemies.html',
                    controller: 'EpidemieController',
                    controllerAs: 'vm',
             resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('epidemie');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('epidemie-detail', {
            parent: 'epidemie',
            url: '/epidemie/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.epidemie.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/epidemie/epidemie-detail.html',
                    controller: 'EpidemieDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('epidemie');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Epidemie', function($stateParams, Epidemie) {
                    return Epidemie.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'epidemie',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('epidemie-detail.edit', {
            parent: 'epidemie-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/epidemie/epidemie-dialog.html',
                    controller: 'EpidemieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Epidemie', function(Epidemie) {
                            return Epidemie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('epidemie.new', {
            parent: 'epidemie',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/epidemie/epidemie-dialog.html',
                    controller: 'EpidemieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                date: null,
                                desciption: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('epidemie', null, { reload: 'epidemie' });
                }, function() {
                    $state.go('epidemie');
                });
            }]
        })
        .state('epidemie.edit', {
            parent: 'epidemie',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/epidemie/epidemie-dialog.html',
                    controller: 'EpidemieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Epidemie', function(Epidemie) {
                            return Epidemie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('epidemie', null, { reload: 'epidemie' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('epidemie.delete', {
            parent: 'epidemie',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/epidemie/epidemie-delete-dialog.html',
                    controller: 'EpidemieDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Epidemie', function(Epidemie) {
                            return Epidemie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('epidemie', null, { reload: 'epidemie' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
