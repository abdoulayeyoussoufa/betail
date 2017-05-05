(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('BetailDeleteController',BetailDeleteController);

    BetailDeleteController.$inject = ['$uibModalInstance', 'entity', 'Betail'];

    function BetailDeleteController($uibModalInstance, entity, Betail) {
        var vm = this;

        vm.betail = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Betail.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
