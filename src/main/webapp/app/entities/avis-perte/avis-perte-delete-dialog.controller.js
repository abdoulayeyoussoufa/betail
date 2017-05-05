(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('AvisPerteDeleteController',AvisPerteDeleteController);

    AvisPerteDeleteController.$inject = ['$uibModalInstance', 'entity', 'AvisPerte'];

    function AvisPerteDeleteController($uibModalInstance, entity, AvisPerte) {
        var vm = this;

        vm.avisPerte = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AvisPerte.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
