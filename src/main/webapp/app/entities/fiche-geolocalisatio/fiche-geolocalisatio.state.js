(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fiche-geolocalisatio', {
            parent: 'app',
            url: '/fiche-geolocalisatio',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.ficheGeolocalisatio.home.title'
            },
            
                    templateUrl: 'app/entities/fiche-geolocalisatio/fiche-geolocalisatios.html',
                    controller: 'FicheGeolocalisatioController',
                    controllerAs: 'vm',
              
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ficheGeolocalisatio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('fiche-geolocalisatio-detail', {
            parent: 'fiche-geolocalisatio',
            url: '/fiche-geolocalisatio/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.ficheGeolocalisatio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fiche-geolocalisatio/fiche-geolocalisatio-detail.html',
                    controller: 'FicheGeolocalisatioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ficheGeolocalisatio');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FicheGeolocalisatio', function($stateParams, FicheGeolocalisatio) {
                    return FicheGeolocalisatio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fiche-geolocalisatio',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('fiche-geolocalisatio-detail.edit', {
            parent: 'fiche-geolocalisatio-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-geolocalisatio/fiche-geolocalisatio-dialog.html',
                    controller: 'FicheGeolocalisatioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FicheGeolocalisatio', function(FicheGeolocalisatio) {
                            return FicheGeolocalisatio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fiche-geolocalisatio.new', {
            parent: 'fiche-geolocalisatio',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-geolocalisatio/fiche-geolocalisatio-dialog.html',
                    controller: 'FicheGeolocalisatioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                latitude: null,
                                longitude: null,
                                precision: null,
                                altitude: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fiche-geolocalisatio', null, { reload: 'fiche-geolocalisatio' });
                }, function() {
                    $state.go('fiche-geolocalisatio');
                });
            }]
        })
        .state('fiche-geolocalisatio.edit', {
            parent: 'fiche-geolocalisatio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-geolocalisatio/fiche-geolocalisatio-dialog.html',
                    controller: 'FicheGeolocalisatioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FicheGeolocalisatio', function(FicheGeolocalisatio) {
                            return FicheGeolocalisatio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fiche-geolocalisatio', null, { reload: 'fiche-geolocalisatio' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fiche-geolocalisatio.delete', {
            parent: 'fiche-geolocalisatio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fiche-geolocalisatio/fiche-geolocalisatio-delete-dialog.html',
                    controller: 'FicheGeolocalisatioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FicheGeolocalisatio', function(FicheGeolocalisatio) {
                            return FicheGeolocalisatio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fiche-geolocalisatio', null, { reload: 'fiche-geolocalisatio' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
