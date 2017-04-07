(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('Ayant_droitDeleteController',Ayant_droitDeleteController);

    Ayant_droitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ayant_droit'];

    function Ayant_droitDeleteController($uibModalInstance, entity, Ayant_droit) {
        var vm = this;

        vm.ayant_droit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ayant_droit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
