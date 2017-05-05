(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheGeolocalisatioDialogController', FicheGeolocalisatioDialogController);

    FicheGeolocalisatioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FicheGeolocalisatio'];

    function FicheGeolocalisatioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FicheGeolocalisatio) {
        var vm = this;

        vm.ficheGeolocalisatio = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ficheGeolocalisatio.id !== null) {
                FicheGeolocalisatio.update(vm.ficheGeolocalisatio, onSaveSuccess, onSaveError);
            } else {
                FicheGeolocalisatio.save(vm.ficheGeolocalisatio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:ficheGeolocalisatioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
