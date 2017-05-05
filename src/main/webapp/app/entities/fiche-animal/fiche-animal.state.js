(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fiche-animal', {
            parent: 'app',
            url: '/fiche-animal',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.ficheAnimal.home.title'
            },
            
                    templateUrl: 'app/entities/fiche-animal/fiche-animals.html',
                    controller: 'FicheAnimalController',
                    controllerAs: 'vm',

            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ficheAnimal');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('fiche-animal-detail', {
            parent: 'fiche-animal',
            url: '/fiche-animal/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.ficheAnimal.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fiche-animal/fiche-animal-detail.html',
                    controller: 'FicheAnimalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ficheAnimal');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FicheAnimal', function($stateParams, FicheAnimal) {
                    return FicheAnimal.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fiche-animal',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('fiche-animal-detail.edit', {
            parent: 'fiche-animal-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-animal/fiche-animal-dialog.html',
                    controller: 'FicheAnimalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FicheAnimal', function(FicheAnimal) {
                            return FicheAnimal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fiche-animal.new', {
            parent: 'fiche-animal',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-animal/fiche-animal-dialog.html',
                    controller: 'FicheAnimalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateDec: null,
                                dateNais: null,
                                lieuNais: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fiche-animal', null, { reload: 'fiche-animal' });
                }, function() {
                    $state.go('fiche-animal');
                });
            }]
        })
        .state('fiche-animal.edit', {
            parent: 'fiche-animal',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-animal/fiche-animal-dialog.html',
                    controller: 'FicheAnimalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FicheAnimal', function(FicheAnimal) {
                            return FicheAnimal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fiche-animal', null, { reload: 'fiche-animal' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fiche-animal.delete', {
            parent: 'fiche-animal',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-animal/fiche-animal-delete-dialog.html',
                    controller: 'FicheAnimalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FicheAnimal', function(FicheAnimal) {
                            return FicheAnimal.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fiche-animal', null, { reload: 'fiche-animal' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
