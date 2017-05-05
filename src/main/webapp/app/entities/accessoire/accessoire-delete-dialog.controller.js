(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('AccessoireDeleteController',AccessoireDeleteController);

    AccessoireDeleteController.$inject = ['$uibModalInstance', 'entity', 'Accessoire'];

    function AccessoireDeleteController($uibModalInstance, entity, Accessoire) {
        var vm = this;

        vm.accessoire = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Accessoire.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
