(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('EleveurDialogController', EleveurDialogController);

    EleveurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Eleveur'];

    function EleveurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Eleveur) {
        var vm = this;

        vm.eleveur = entity;
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
            if (vm.eleveur.id !== null) {
                Eleveur.update(vm.eleveur, onSaveSuccess, onSaveError);
            } else {
                Eleveur.save(vm.eleveur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:eleveurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
