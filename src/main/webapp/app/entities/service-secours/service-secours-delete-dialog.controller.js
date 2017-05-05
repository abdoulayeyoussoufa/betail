(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('ServiceSecoursDeleteController',ServiceSecoursDeleteController);

    ServiceSecoursDeleteController.$inject = ['$uibModalInstance', 'entity', 'ServiceSecours'];

    function ServiceSecoursDeleteController($uibModalInstance, entity, ServiceSecours) {
        var vm = this;

        vm.serviceSecours = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ServiceSecours.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
