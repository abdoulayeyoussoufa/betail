(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('betail', {
            parent: 'app',
            url: '/betail?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.betail.home.title'
            },
            
                    templateUrl: 'app/entities/betail/betails.html',
                    controller: 'BetailController',
                    controllerAs: 'vm'
                ,
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('betail');
                    $translatePartialLoader.addPart('sexeEnum');
                    $translatePartialLoader.addPart('vitesseEnum');
                    $translatePartialLoader.addPart('enclosEnum');
                    $translatePartialLoader.addPart('situationEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('betail-detail', {
            parent: 'app',
            url: '/betail/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.betail.detail.title'
            },
            
                    templateUrl: 'app/entities/betail/betail-detail.html',
                    controller: 'BetailDetailController',
                    controllerAs: 'vm'
              ,
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('betail');
                    $translatePartialLoader.addPart('sexeEnum');
                    $translatePartialLoader.addPart('vitesseEnum');
                    $translatePartialLoader.addPart('enclosEnum');
                    $translatePartialLoader.addPart('situationEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Betail', function($stateParams, Betail) {
                    return Betail.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'betail',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('betail-detail.edit', {
            parent: 'betail-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/betail/betail-dialog.html',
                    controller: 'BetailDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Betail', function(Betail) {
                            return Betail.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('betail.new', {
            parent: 'betail',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/betail/betail-dialog.html',
                    controller: 'BetailDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                sexe: null,
                                race: null,
                                couleurRobe: null,
                                vitesse: null,
                                enclos: null,
                                situation: null,
                                poids: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('betail', null, { reload: 'betail' });
                }, function() {
                    $state.go('betail');
                });
            }]
        })
        .state('betail.edit', {
            parent: 'betail',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/betail/betail-dialog.html',
                    controller: 'BetailDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Betail', function(Betail) {
                            return Betail.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('betail', null, { reload: 'betail' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('betail.delete', {
            parent: 'betail',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/betail/betail-delete-dialog.html',
                    controller: 'BetailDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Betail', function(Betail) {
                            return Betail.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('betail', null, { reload: 'betail' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
