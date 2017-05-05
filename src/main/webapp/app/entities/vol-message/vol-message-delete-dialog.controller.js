(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('VolMessageDeleteController',VolMessageDeleteController);

    VolMessageDeleteController.$inject = ['$uibModalInstance', 'entity', 'VolMessage'];

    function VolMessageDeleteController($uibModalInstance, entity, VolMessage) {
        var vm = this;

        vm.volMessage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VolMessage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
