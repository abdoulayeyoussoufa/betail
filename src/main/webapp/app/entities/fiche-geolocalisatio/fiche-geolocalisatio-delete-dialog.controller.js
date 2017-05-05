(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheGeolocalisatioDeleteController',FicheGeolocalisatioDeleteController);

    FicheGeolocalisatioDeleteController.$inject = ['$uibModalInstance', 'entity', 'FicheGeolocalisatio'];

    function FicheGeolocalisatioDeleteController($uibModalInstance, entity, FicheGeolocalisatio) {
        var vm = this;

        vm.ficheGeolocalisatio = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FicheGeolocalisatio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
