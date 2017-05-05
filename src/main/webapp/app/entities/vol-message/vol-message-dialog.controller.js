(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('VolMessageDialogController', VolMessageDialogController);

    VolMessageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VolMessage'];

    function VolMessageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VolMessage) {
        var vm = this;

        vm.volMessage = entity;
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
            if (vm.volMessage.id !== null) {
                VolMessage.update(vm.volMessage, onSaveSuccess, onSaveError);
            } else {
                VolMessage.save(vm.volMessage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:volMessageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
