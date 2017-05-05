(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('EleveurDeleteController',EleveurDeleteController);

    EleveurDeleteController.$inject = ['$uibModalInstance', 'entity', 'Eleveur'];

    function EleveurDeleteController($uibModalInstance, entity, Eleveur) {
        var vm = this;

        vm.eleveur = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Eleveur.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
