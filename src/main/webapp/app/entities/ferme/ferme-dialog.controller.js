(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FermeDialogController', FermeDialogController);

    FermeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ferme'];

    function FermeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ferme) {
        var vm = this;

        vm.ferme = entity;
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
            if (vm.ferme.id !== null) {
                Ferme.update(vm.ferme, onSaveSuccess, onSaveError);
            } else {
                Ferme.save(vm.ferme, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:fermeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
