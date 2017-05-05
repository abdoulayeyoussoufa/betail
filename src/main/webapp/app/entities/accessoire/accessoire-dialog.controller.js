(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('AccessoireDialogController', AccessoireDialogController);

    AccessoireDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Accessoire'];

    function AccessoireDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Accessoire) {
        var vm = this;

        vm.accessoire = entity;
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
            if (vm.accessoire.id !== null) {
                Accessoire.update(vm.accessoire, onSaveSuccess, onSaveError);
            } else {
                Accessoire.save(vm.accessoire, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:accessoireUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
