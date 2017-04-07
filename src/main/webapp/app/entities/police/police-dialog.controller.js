(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('PoliceDialogController', PoliceDialogController);

    PoliceDialogController.$inject = ['$timeout', '$scope', '$stateParams', 'entity', 'Police'];

    function PoliceDialogController ($timeout, $scope, $stateParams, entity, Police) {
        var vm = this;
        vm.police = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            //$uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.police.id !== null) {
                Police.update(vm.police, onSaveSuccess, onSaveError);
            } else {
                Police.save(vm.police, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mutuplexApp:policeUpdate', result);
            //$uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateDebut = false;
        vm.datePickerOpenStatus.dateFin = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
