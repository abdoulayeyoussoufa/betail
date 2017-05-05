(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheTracabiliteDialogController', FicheTracabiliteDialogController);

    FicheTracabiliteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FicheTracabilite'];

    function FicheTracabiliteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FicheTracabilite) {
        var vm = this;

        vm.ficheTracabilite = entity;
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
            if (vm.ficheTracabilite.id !== null) {
                FicheTracabilite.update(vm.ficheTracabilite, onSaveSuccess, onSaveError);
            } else {
                FicheTracabilite.save(vm.ficheTracabilite, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:ficheTracabiliteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateObt = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
