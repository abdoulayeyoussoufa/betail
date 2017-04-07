(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('Ayant_droitDialogController', Ayant_droitDialogController);

    Ayant_droitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ayant_droit', 'Assure'];

    function Ayant_droitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ayant_droit, Assure) {
        var vm = this;

        vm.ayant_droit = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.assures = Assure.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ayant_droit.id !== null) {
                Ayant_droit.update(vm.ayant_droit, onSaveSuccess, onSaveError);
            } else {
                Ayant_droit.save(vm.ayant_droit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mutuplexApp:ayant_droitUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date_naissance = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
