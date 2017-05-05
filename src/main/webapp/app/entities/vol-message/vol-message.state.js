(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('vol-message', {
            parent: 'app',
            url: '/vol-message',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.volMessage.home.title'
            },
            
                    templateUrl: 'app/entities/vol-message/vol-messages.html',
                    controller: 'VolMessageController',
                    controllerAs: 'vm',

            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('volMessage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('vol-message-detail', {
            parent: 'vol-message',
            url: '/vol-message/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.volMessage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vol-message/vol-message-detail.html',
                    controller: 'VolMessageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('volMessage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'VolMessage', function($stateParams, VolMessage) {
                    return VolMessage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'vol-message',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('vol-message-detail.edit', {
            parent: 'vol-message-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vol-message/vol-message-dialog.html',
                    controller: 'VolMessageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VolMessage', function(VolMessage) {
                            return VolMessage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vol-message.new', {
            parent: 'vol-message',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vol-message/vol-message-dialog.html',
                    controller: 'VolMessageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('vol-message', null, { reload: 'vol-message' });
                }, function() {
                    $state.go('vol-message');
                });
            }]
        })
        .state('vol-message.edit', {
            parent: 'vol-message',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vol-message/vol-message-dialog.html',
                    controller: 'VolMessageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VolMessage', function(VolMessage) {
                            return VolMessage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vol-message', null, { reload: 'vol-message' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vol-message.delete', {
            parent: 'vol-message',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vol-message/vol-message-delete-dialog.html',
                    controller: 'VolMessageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VolMessage', function(VolMessage) {
                            return VolMessage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vol-message', null, { reload: 'vol-message' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
