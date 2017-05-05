(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('service-secours', {
            parent: 'app',
            url: '/service-secours',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.serviceSecours.home.title'
            },
           
                    templateUrl: 'app/entities/service-secours/service-secours.html',
                    controller: 'ServiceSecoursController',
                    controllerAs: 'vm',

            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('serviceSecours');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('service-secours-detail', {
            parent: 'service-secours',
            url: '/service-secours/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.serviceSecours.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/service-secours/service-secours-detail.html',
                    controller: 'ServiceSecoursDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('serviceSecours');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ServiceSecours', function($stateParams, ServiceSecours) {
                    return ServiceSecours.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'service-secours',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('service-secours-detail.edit', {
            parent: 'service-secours-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-secours/service-secours-dialog.html',
                    controller: 'ServiceSecoursDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServiceSecours', function(ServiceSecours) {
                            return ServiceSecours.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('service-secours.new', {
            parent: 'service-secours',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-secours/service-secours-dialog.html',
                    controller: 'ServiceSecoursDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                siege: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('service-secours', null, { reload: 'service-secours' });
                }, function() {
                    $state.go('service-secours');
                });
            }]
        })
        .state('service-secours.edit', {
            parent: 'service-secours',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-secours/service-secours-dialog.html',
                    controller: 'ServiceSecoursDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServiceSecours', function(ServiceSecours) {
                            return ServiceSecours.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('service-secours', null, { reload: 'service-secours' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('service-secours.delete', {
            parent: 'service-secours',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-secours/service-secours-delete-dialog.html',
                    controller: 'ServiceSecoursDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ServiceSecours', function(ServiceSecours) {
                            return ServiceSecours.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('service-secours', null, { reload: 'service-secours' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
