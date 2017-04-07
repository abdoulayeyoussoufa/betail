(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ayant-droit', {
            parent: 'app',
            url: '/ayant-droit',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mutuplexApp.ayant_droit.home.title'
            },
                   templateUrl: 'app/entities/ayant-droit/ayant-droits.html',
                    controller: 'Ayant_droitController',
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ayant_droit');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ayant-droit-detail', {
            parent: 'entity',
            url: '/ayant-droit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mutuplexApp.ayant_droit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ayant-droit/ayant-droit-detail.html',
                    controller: 'Ayant_droitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ayant_droit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ayant_droit', function($stateParams, Ayant_droit) {
                    return Ayant_droit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ayant-droit',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ayant-droit-detail.edit', {
            parent: 'ayant-droit-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ayant-droit/ayant-droit-dialog.html',
                    controller: 'Ayant_droitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ayant_droit', function(Ayant_droit) {
                            return Ayant_droit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ayant-droit.new', {
            parent: 'ayant-droit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ayant-droit/ayant-droit-dialog.html',
                    controller: 'Ayant_droitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                prenom: null,
                                sexe: null,
                                lien_parent: null,
                                date_naissance: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ayant-droit', null, { reload: 'ayant-droit' });
                }, function() {
                    $state.go('ayant-droit');
                });
            }]
        })
        .state('ayant-droit.edit', {
            parent: 'ayant-droit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ayant-droit/ayant-droit-dialog.html',
                    controller: 'Ayant_droitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ayant_droit', function(Ayant_droit) {
                            return Ayant_droit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ayant-droit', null, { reload: 'ayant-droit' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ayant-droit.delete', {
            parent: 'ayant-droit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ayant-droit/ayant-droit-delete-dialog.html',
                    controller: 'Ayant_droitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ayant_droit', function(Ayant_droit) {
                            return Ayant_droit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ayant-droit', null, { reload: 'ayant-droit' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
