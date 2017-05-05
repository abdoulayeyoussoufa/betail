(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FermeDeleteController',FermeDeleteController);

    FermeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ferme'];

    function FermeDeleteController($uibModalInstance, entity, Ferme) {
        var vm = this;

        vm.ferme = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ferme.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
