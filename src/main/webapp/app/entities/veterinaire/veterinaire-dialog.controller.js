(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('VeterinaireDialogController', VeterinaireDialogController);

    VeterinaireDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Veterinaire'];

    function VeterinaireDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Veterinaire) {
        var vm = this;

        vm.veterinaire = entity;
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
            if (vm.veterinaire.id !== null) {
                Veterinaire.update(vm.veterinaire, onSaveSuccess, onSaveError);
            } else {
                Veterinaire.save(vm.veterinaire, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:veterinaireUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
