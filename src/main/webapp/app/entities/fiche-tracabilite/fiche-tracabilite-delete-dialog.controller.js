(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheTracabiliteDeleteController',FicheTracabiliteDeleteController);

    FicheTracabiliteDeleteController.$inject = ['$uibModalInstance', 'entity', 'FicheTracabilite'];

    function FicheTracabiliteDeleteController($uibModalInstance, entity, FicheTracabilite) {
        var vm = this;

        vm.ficheTracabilite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FicheTracabilite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
