(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('PoliceDialogController', PoliceDialogController);

    PoliceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Police'];

    function PoliceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Police) {
        var vm = this;

        vm.police = entity;
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
            if (vm.police.id !== null) {
                Police.update(vm.police, onSaveSuccess, onSaveError);
            } else {
                Police.save(vm.police, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:policeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
