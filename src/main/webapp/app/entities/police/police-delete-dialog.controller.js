(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('PoliceDeleteController',PoliceDeleteController);

    PoliceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Police'];

    function PoliceDeleteController($uibModalInstance, entity, Police) {
        var vm = this;

        vm.police = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Police.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
