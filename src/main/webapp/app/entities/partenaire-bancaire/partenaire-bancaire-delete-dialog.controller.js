(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('PartenaireBancaireDeleteController',PartenaireBancaireDeleteController);

    PartenaireBancaireDeleteController.$inject = ['$uibModalInstance', 'entity', 'PartenaireBancaire'];

    function PartenaireBancaireDeleteController($uibModalInstance, entity, PartenaireBancaire) {
        var vm = this;

        vm.partenaireBancaire = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PartenaireBancaire.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
