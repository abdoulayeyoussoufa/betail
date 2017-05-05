(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('CarnetSanteDeleteController',CarnetSanteDeleteController);

    CarnetSanteDeleteController.$inject = ['$uibModalInstance', 'entity', 'CarnetSante'];

    function CarnetSanteDeleteController($uibModalInstance, entity, CarnetSante) {
        var vm = this;

        vm.carnetSante = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CarnetSante.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
