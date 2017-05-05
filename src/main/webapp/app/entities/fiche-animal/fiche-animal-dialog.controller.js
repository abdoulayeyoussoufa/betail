(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheAnimalDialogController', FicheAnimalDialogController);

    FicheAnimalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FicheAnimal'];

    function FicheAnimalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FicheAnimal) {
        var vm = this;

        vm.ficheAnimal = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ficheAnimal.id !== null) {
                FicheAnimal.update(vm.ficheAnimal, onSaveSuccess, onSaveError);
            } else {
                FicheAnimal.save(vm.ficheAnimal, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:ficheAnimalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateDec = false;
        vm.datePickerOpenStatus.dateNais = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
