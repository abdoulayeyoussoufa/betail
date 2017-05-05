(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('MaladieDialogController', MaladieDialogController);

    MaladieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Maladie'];

    function MaladieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Maladie) {
        var vm = this;

        vm.maladie = entity;
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
            if (vm.maladie.id !== null) {
                Maladie.update(vm.maladie, onSaveSuccess, onSaveError);
            } else {
                Maladie.save(vm.maladie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:maladieUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
