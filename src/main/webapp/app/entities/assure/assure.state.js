(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('assure', {
            parent: 'app',
            url: '/assure',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mutuplexApp.assure.home.title'
            },
                    templateUrl: 'app/entities/assure/assures.html',
                    controller: 'AssureController',
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
                    $translatePartialLoader.addPart('assure');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('assure-detail', {
            parent: 'app',
            url: '/assure/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mutuplexApp.assure.detail.title'
            },
                  templateUrl: 'app/entities/assure/assure-detail.html',
                    controller: 'AssureDetailController',
                    controllerAs: 'vm'
               ,
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('assure');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Assure', function($stateParams, Assure) {
                    return Assure.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'assure',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('assure-detail.edit', {
            parent: 'assure-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assure/assure-dialog.html',
                    controller: 'AssureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Assure', function(Assure) {
                            return Assure.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('assure.new', {
            parent: 'assure',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assure/assure-dialog.html',
                    controller: 'AssureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                prenom: null,
                                police: null,
                                groupe: null,
                                sexe: null,
                                date_naissance: null,
                                lieu: null,
                                email: null,
                                telF: null,
                                telP: null,
                                matricule: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('assure', null, { reload: 'assure' });
                }, function() {
                    $state.go('assure');
                });
            }]
        })
        .state('assure.edit', {
            parent: 'assure',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assure/assure-dialog.html',
                    controller: 'AssureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Assure', function(Assure) {
                            return Assure.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('assure', null, { reload: 'assure' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('assure.delete', {
            parent: 'assure',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/assure/assure-delete-dialog.html',
                    controller: 'AssureDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Assure', function(Assure) {
                            return Assure.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('assure', null, { reload: 'assure' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
