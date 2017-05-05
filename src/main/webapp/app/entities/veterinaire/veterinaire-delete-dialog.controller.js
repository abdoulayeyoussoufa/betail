(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('VeterinaireDeleteController',VeterinaireDeleteController);

    VeterinaireDeleteController.$inject = ['$uibModalInstance', 'entity', 'Veterinaire'];

    function VeterinaireDeleteController($uibModalInstance, entity, Veterinaire) {
        var vm = this;

        vm.veterinaire = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Veterinaire.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
