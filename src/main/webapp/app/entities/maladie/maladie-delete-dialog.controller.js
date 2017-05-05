(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('MaladieDeleteController',MaladieDeleteController);

    MaladieDeleteController.$inject = ['$uibModalInstance', 'entity', 'Maladie'];

    function MaladieDeleteController($uibModalInstance, entity, Maladie) {
        var vm = this;

        vm.maladie = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Maladie.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
