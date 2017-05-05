(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('TroupeauDeleteController',TroupeauDeleteController);

    TroupeauDeleteController.$inject = ['$uibModalInstance', 'entity', 'Troupeau'];

    function TroupeauDeleteController($uibModalInstance, entity, Troupeau) {
        var vm = this;

        vm.troupeau = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Troupeau.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
