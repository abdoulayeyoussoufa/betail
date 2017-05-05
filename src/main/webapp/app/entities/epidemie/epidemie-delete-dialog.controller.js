(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('EpidemieDeleteController',EpidemieDeleteController);

    EpidemieDeleteController.$inject = ['$uibModalInstance', 'entity', 'Epidemie'];

    function EpidemieDeleteController($uibModalInstance, entity, Epidemie) {
        var vm = this;

        vm.epidemie = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Epidemie.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
