(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('BetailDialogController', BetailDialogController);

    BetailDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Betail'];

    function BetailDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Betail) {
        var vm = this;

        vm.betail = entity;
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
            if (vm.betail.id !== null) {
                Betail.update(vm.betail, onSaveSuccess, onSaveError);
            } else {
                Betail.save(vm.betail, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:betailUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
