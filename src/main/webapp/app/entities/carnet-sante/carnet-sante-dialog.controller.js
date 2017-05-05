(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('CarnetSanteDialogController', CarnetSanteDialogController);

    CarnetSanteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CarnetSante'];

    function CarnetSanteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CarnetSante) {
        var vm = this;

        vm.carnetSante = entity;
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
            if (vm.carnetSante.id !== null) {
                CarnetSante.update(vm.carnetSante, onSaveSuccess, onSaveError);
            } else {
                CarnetSante.save(vm.carnetSante, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:carnetSanteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datVaccination = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
