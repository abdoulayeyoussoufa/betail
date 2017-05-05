(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('personne', {
            parent: 'app',
            url: '/personne?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.personne.home.title'
            },
           
                    templateUrl: 'app/entities/personne/personnes.html',
                    controller: 'PersonneController',
                    controllerAs: 'vm',
  
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
                    $translatePartialLoader.addPart('personne');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('personne-detail', {
            parent: 'personne',
            url: '/personne/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'volBetailApp.personne.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personne/personne-detail.html',
                    controller: 'PersonneDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personne');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Personne', function($stateParams, Personne) {
                    return Personne.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'personne',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('personne-detail.edit', {
            parent: 'personne-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personne/personne-dialog.html',
                    controller: 'PersonneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personne', function(Personne) {
                            return Personne.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personne.new', {
            parent: 'personne',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personne/personne-dialog.html',
                    controller: 'PersonneDialogController',
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
                    $state.go('personne', null, { reload: 'personne' });
                }, function() {
                    $state.go('personne');
                });
            }]
        })
        .state('personne.edit', {
            parent: 'personne',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personne/personne-dialog.html',
                    controller: 'PersonneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personne', function(Personne) {
                            return Personne.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personne', null, { reload: 'personne' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personne.delete', {
            parent: 'personne',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personne/personne-delete-dialog.html',
                    controller: 'PersonneDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Personne', function(Personne) {
                            return Personne.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personne', null, { reload: 'personne' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
