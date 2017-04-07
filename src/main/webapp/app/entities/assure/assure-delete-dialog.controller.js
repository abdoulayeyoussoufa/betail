(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('AssureDeleteController',AssureDeleteController);

    AssureDeleteController.$inject = ['$uibModalInstance', 'entity', 'Assure'];

    function AssureDeleteController($uibModalInstance, entity, Assure) {
        var vm = this;

        vm.assure = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Assure.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
