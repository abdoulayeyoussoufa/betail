(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('veterinaire', {
            parent: 'app',
            url: '/veterinaire',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.veterinaire.home.title'
            },
           
                    templateUrl: 'app/entities/veterinaire/veterinaires.html',
                    controller: 'VeterinaireController',
                    controllerAs: 'vm',
  
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('veterinaire');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('veterinaire-detail', {
            parent: 'veterinaire',
            url: '/veterinaire/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.veterinaire.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/veterinaire/veterinaire-detail.html',
                    controller: 'VeterinaireDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('veterinaire');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Veterinaire', function($stateParams, Veterinaire) {
                    return Veterinaire.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'veterinaire',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('veterinaire-detail.edit', {
            parent: 'veterinaire-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/veterinaire/veterinaire-dialog.html',
                    controller: 'VeterinaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Veterinaire', function(Veterinaire) {
                            return Veterinaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('veterinaire.new', {
            parent: 'veterinaire',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/veterinaire/veterinaire-dialog.html',
                    controller: 'VeterinaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                prenom: null,
                                nom: null,
                                email: null,
                                telephone: null,
                                numeroCNI: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('veterinaire', null, { reload: 'veterinaire' });
                }, function() {
                    $state.go('veterinaire');
                });
            }]
        })
        .state('veterinaire.edit', {
            parent: 'veterinaire',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/veterinaire/veterinaire-dialog.html',
                    controller: 'VeterinaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Veterinaire', function(Veterinaire) {
                            return Veterinaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('veterinaire', null, { reload: 'veterinaire' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('veterinaire.delete', {
            parent: 'veterinaire',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/veterinaire/veterinaire-delete-dialog.html',
                    controller: 'VeterinaireDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Veterinaire', function(Veterinaire) {
                            return Veterinaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('veterinaire', null, { reload: 'veterinaire' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
