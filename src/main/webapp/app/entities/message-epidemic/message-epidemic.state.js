(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('message-epidemic', {
            parent: 'app',
            url: '/message-epidemic',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.messageEpidemic.home.title'
            },
           
                    templateUrl: 'app/entities/message-epidemic/message-epidemics.html',
                    controller: 'MessageEpidemicController',
                    controllerAs: 'vm',
    
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('messageEpidemic');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('message-epidemic-detail', {
            parent: 'message-epidemic',
            url: '/message-epidemic/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.messageEpidemic.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/message-epidemic/message-epidemic-detail.html',
                    controller: 'MessageEpidemicDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('messageEpidemic');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MessageEpidemic', function($stateParams, MessageEpidemic) {
                    return MessageEpidemic.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'message-epidemic',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('message-epidemic-detail.edit', {
            parent: 'message-epidemic-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/message-epidemic/message-epidemic-dialog.html',
                    controller: 'MessageEpidemicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MessageEpidemic', function(MessageEpidemic) {
                            return MessageEpidemic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('message-epidemic.new', {
            parent: 'message-epidemic',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/message-epidemic/message-epidemic-dialog.html',
                    controller: 'MessageEpidemicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                date: null,
                                lieu: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('message-epidemic', null, { reload: 'message-epidemic' });
                }, function() {
                    $state.go('message-epidemic');
                });
            }]
        })
        .state('message-epidemic.edit', {
            parent: 'message-epidemic',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/message-epidemic/message-epidemic-dialog.html',
                    controller: 'MessageEpidemicDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MessageEpidemic', function(MessageEpidemic) {
                            return MessageEpidemic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('message-epidemic', null, { reload: 'message-epidemic' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('message-epidemic.delete', {
            parent: 'message-epidemic',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/message-epidemic/message-epidemic-delete-dialog.html',
                    controller: 'MessageEpidemicDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MessageEpidemic', function(MessageEpidemic) {
                            return MessageEpidemic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('message-epidemic', null, { reload: 'message-epidemic' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
