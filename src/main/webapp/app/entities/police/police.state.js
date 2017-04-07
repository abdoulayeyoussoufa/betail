(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('police', {
            parent: 'app',
            url: '/police?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mutuplexApp.police.home.title'
            },
                    templateUrl: 'app/entities/police/police.html',
                    controller: 'PoliceController',
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
                    $translatePartialLoader.addPart('police');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('police-detail', {
            parent: 'app',
            url: '/police/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mutuplexApp.police.detail.title'
            },
                    templateUrl: 'app/entities/police/police-detail.html',
                    controller: 'PoliceDetailController',
                    controllerAs: 'vm',
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('police');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Police', function($stateParams, Police) {
                    return Police.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'police',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('police-add', {
            parent: 'app',
            url: '/policeadd',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mutuplexApp.police.detail.title'
            },
            templateUrl: 'app/entities/police/police-add.html',
            controller: 'PoliceDialogController',
            controllerAs: 'vm',
            resolve: {
                entity: function () {
                    return {
                        raison: null,
                        adresse: null,
                        tel: null,
                        email: null,
                        interloc: null,
                        dateDebut: null,
                        dateFin: null,
                        tiers: null,
                        tpx: null,
                        activite: null,
                        formule_soins: null,
                        territoire: null,
                        plafond: null,
                        complement: null,
                        consultation_taux: null,
                        consultation_frais: null,
                        consultation_limite: null,
                        consultation_jour: null,
                        consultation_an: null,
                        consultation_deux_an: null,
                        pharmacie_taux: null,
                        pharmacie_frais: null,
                        pharmacie_limite: null,
                        pharmacie_jour: null,
                        pharmacie_an: null,
                        pharmacie_deux_an: null,
                        analyse_taux: null,
                        analyse_frais: null,
                        analyse_limite: null,
                        analyse_jour: null,
                        analyse_an: null,
                        analyse_deux_an: null,
                        acte_taux: null,
                        acte_frais: null,
                        acte_limite: null,
                        acte_jour: null,
                        acte_an: null,
                        acte_deux_an: null,
                        principal_chambre_taux: null,
                        principal_chambre_frais: null,
                        principal_chambre_limite: null,
                        principal_chambre_jour: null,
                        principal_chambre_an: null,
                        principal_chambre_deux_an: null,
                        principal_frais_taux: null,
                        principal_frais_limite: null,
                        principal_frais_jour: null,
                        principal_frais_an: null,
                        principal_frais_deux_an: null,
                        prive_chambre_taux: null,
                        prive_chambre_frais: null,
                        prive_chambre_limite: null,
                        prive_chambre_jour: null,
                        prive_chambre_an: null,
                        prive_chambre_deux_an: null,
                        prive_frais_taux: null,
                        prive_frais_limite: null,
                        prive_frais_jour: null,
                        prive_frais_an: null,
                        prive_frais_deux_an: null,
                        public_chambre_taux: null,
                        public_chambre_frais: null,
                        public_chambre_limite: null,
                        public_chambre_jour: null,
                        public_chambre_an: null,
                        public_chambre_deux_an: null,
                        public_frais_taux: null,
                        public_frais_frais: null,
                        public_frais_limite: null,
                        public_frais_jour: null,
                        public_frais_an: null,
                        public_frais_deux_an: null,
                        soins_taux: null,
                        soins_frais: null,
                        soins_limite: null,
                        soins_jour: null,
                        soins_an: null,
                        soins_deux_an: null,
                        verre_taux: null,
                        verre_frais: null,
                        verre_limite: null,
                        verre_jour: null,
                        verre_an: null,
                        verre_deux_an: null,
                        monture_taux: null,
                        monture_frais: null,
                        monture_limite: null,
                        monture_jour: null,
                        monture_an: null,
                        monture_deux_an: null,
                        accouchement_taux: null,
                        accouchement_frais: null,
                        accouchement_limite: null,
                        accouchement_jour: null,
                        accouchement_an: null,
                        accouchement_deux_an: null,
                        education_taux: null,
                        education_frais: null,
                        education_limite: null,
                        education_jour: null,
                        education_an: null,
                        education_deux_an: null,
                        sejour_taux: null,
                        sejour_frais: null,
                        sejour_limite: null,
                        sejour_jour: null,
                        sejour_an: null,
                        sejour_deux_an: null,
                        nbCollab: null,
                        nbConjoint: null,
                        nbEnfantP: null,
                        nbEnfantG: null,
                        fin: null,
                        id: null
                    };
                },
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('police');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('police-detail.edit', {
            parent: 'police-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/police/police-dialog.html',
                    controller: 'PoliceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Police', function(Police) {
                            return Police.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('police.new', {
            parent: 'police',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/police/police-dialog.html',
                    controller: 'PoliceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                raison: null,
                                adresse: null,
                                tel: null,
                                email: null,
                                interloc: null,
                                dateDebut: null,
                                dateFin: null,
                                tiers: null,
                                tpx: null,
                                activite: null,
                                formule_soins: null,
                                territoire: null,
                                plafond: null,
                                complement: null,
                                consultation_taux: null,
                                consultation_frais: null,
                                consultation_limite: null,
                                consultation_jour: null,
                                consultation_an: null,
                                consultation_deux_an: null,
                                pharmacie_taux: null,
                                pharmacie_frais: null,
                                pharmacie_limite: null,
                                pharmacie_jour: null,
                                pharmacie_an: null,
                                pharmacie_deux_an: null,
                                analyse_taux: null,
                                analyse_frais: null,
                                analyse_limite: null,
                                analyse_jour: null,
                                analyse_an: null,
                                analyse_deux_an: null,
                                acte_taux: null,
                                acte_frais: null,
                                acte_limite: null,
                                acte_jour: null,
                                acte_an: null,
                                acte_deux_an: null,
                                principal_chambre_taux: null,
                                principal_chambre_frais: null,
                                principal_chambre_limite: null,
                                principal_chambre_jour: null,
                                principal_chambre_an: null,
                                principal_chambre_deux_an: null,
                                principal_frais_taux: null,
                                principal_frais_limite: null,
                                principal_frais_jour: null,
                                principal_frais_an: null,
                                principal_frais_deux_an: null,
                                prive_chambre_taux: null,
                                prive_chambre_frais: null,
                                prive_chambre_limite: null,
                                prive_chambre_jour: null,
                                prive_chambre_an: null,
                                prive_chambre_deux_an: null,
                                prive_frais_taux: null,
                                prive_frais_limite: null,
                                prive_frais_jour: null,
                                prive_frais_an: null,
                                prive_frais_deux_an: null,
                                public_chambre_taux: null,
                                public_chambre_frais: null,
                                public_chambre_limite: null,
                                public_chambre_jour: null,
                                public_chambre_an: null,
                                public_chambre_deux_an: null,
                                public_frais_taux: null,
                                public_frais_frais: null,
                                public_frais_limite: null,
                                public_frais_jour: null,
                                public_frais_an: null,
                                public_frais_deux_an: null,
                                soins_taux: null,
                                soins_frais: null,
                                soins_limite: null,
                                soins_jour: null,
                                soins_an: null,
                                soins_deux_an: null,
                                verre_taux: null,
                                verre_frais: null,
                                verre_limite: null,
                                verre_jour: null,
                                verre_an: null,
                                verre_deux_an: null,
                                monture_taux: null,
                                monture_frais: null,
                                monture_limite: null,
                                monture_jour: null,
                                monture_an: null,
                                monture_deux_an: null,
                                accouchement_taux: null,
                                accouchement_frais: null,
                                accouchement_limite: null,
                                accouchement_jour: null,
                                accouchement_an: null,
                                accouchement_deux_an: null,
                                education_taux: null,
                                education_frais: null,
                                education_limite: null,
                                education_jour: null,
                                education_an: null,
                                education_deux_an: null,
                                sejour_taux: null,
                                sejour_frais: null,
                                sejour_limite: null,
                                sejour_jour: null,
                                sejour_an: null,
                                sejour_deux_an: null,
                                nbCollab: null,
                                nbConjoint: null,
                                nbEnfantP: null,
                                nbEnfantG: null,
                                fin: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('police', null, { reload: 'police' });
                }, function() {
                    $state.go('police');
                });
            }]
        })
        .state('police.edit', {
            parent: 'police',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/police/police-dialog.html',
                    controller: 'PoliceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Police', function(Police) {
                            return Police.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('police', null, { reload: 'police' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('police.delete', {
            parent: 'police',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/police/police-delete-dialog.html',
                    controller: 'PoliceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Police', function(Police) {
                            return Police.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('police', null, { reload: 'police' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
