(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('EpidemieDialogController', EpidemieDialogController);

    EpidemieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Epidemie'];

    function EpidemieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Epidemie) {
        var vm = this;

        vm.epidemie = entity;
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
            if (vm.epidemie.id !== null) {
                Epidemie.update(vm.epidemie, onSaveSuccess, onSaveError);
            } else {
                Epidemie.save(vm.epidemie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:epidemieUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
