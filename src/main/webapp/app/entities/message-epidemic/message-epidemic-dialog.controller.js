(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('MessageEpidemicDialogController', MessageEpidemicDialogController);

    MessageEpidemicDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MessageEpidemic'];

    function MessageEpidemicDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MessageEpidemic) {
        var vm = this;

        vm.messageEpidemic = entity;
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
            if (vm.messageEpidemic.id !== null) {
                MessageEpidemic.update(vm.messageEpidemic, onSaveSuccess, onSaveError);
            } else {
                MessageEpidemic.save(vm.messageEpidemic, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:messageEpidemicUpdate', result);
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
