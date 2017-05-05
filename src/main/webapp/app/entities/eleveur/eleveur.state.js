(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('eleveur', {
            parent: 'app',
            url: '/eleveur',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.eleveur.home.title'
            },
           
                    templateUrl: 'app/entities/eleveur/eleveurs.html',
                    controller: 'EleveurController',
                    controllerAs: 'vm',

            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('eleveur');
                    $translatePartialLoader.addPart('language');
                    $translatePartialLoader.addPart('sexe');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('eleveur-detail', {
            parent: 'eleveur',
            url: '/eleveur/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.eleveur.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/eleveur/eleveur-detail.html',
                    controller: 'EleveurDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('eleveur');
                    $translatePartialLoader.addPart('language');
                    $translatePartialLoader.addPart('sexe');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Eleveur', function($stateParams, Eleveur) {
                    return Eleveur.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'eleveur',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('eleveur-detail.edit', {
            parent: 'eleveur-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eleveur/eleveur-dialog.html',
                    controller: 'EleveurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Eleveur', function(Eleveur) {
                            return Eleveur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eleveur.new', {
            parent: 'eleveur',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eleveur/eleveur-dialog.html',
                    controller: 'EleveurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                prenom: null,
                                nom: null,
                                langue: null,
                                sex: null,
                                email: null,
                                numeroCNI: null,
                                telephone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('eleveur', null, { reload: 'eleveur' });
                }, function() {
                    $state.go('eleveur');
                });
            }]
        })
        .state('eleveur.edit', {
            parent: 'eleveur',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eleveur/eleveur-dialog.html',
                    controller: 'EleveurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Eleveur', function(Eleveur) {
                            return Eleveur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eleveur', null, { reload: 'eleveur' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eleveur.delete', {
            parent: 'eleveur',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eleveur/eleveur-delete-dialog.html',
                    controller: 'EleveurDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Eleveur', function(Eleveur) {
                            return Eleveur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eleveur', null, { reload: 'eleveur' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
