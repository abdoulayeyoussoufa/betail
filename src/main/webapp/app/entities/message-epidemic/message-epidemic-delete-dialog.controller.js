(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('MessageEpidemicDeleteController',MessageEpidemicDeleteController);

    MessageEpidemicDeleteController.$inject = ['$uibModalInstance', 'entity', 'MessageEpidemic'];

    function MessageEpidemicDeleteController($uibModalInstance, entity, MessageEpidemic) {
        var vm = this;

        vm.messageEpidemic = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MessageEpidemic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
