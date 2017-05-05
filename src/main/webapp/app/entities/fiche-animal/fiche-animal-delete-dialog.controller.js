(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheAnimalDeleteController',FicheAnimalDeleteController);

    FicheAnimalDeleteController.$inject = ['$uibModalInstance', 'entity', 'FicheAnimal'];

    function FicheAnimalDeleteController($uibModalInstance, entity, FicheAnimal) {
        var vm = this;

        vm.ficheAnimal = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FicheAnimal.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
