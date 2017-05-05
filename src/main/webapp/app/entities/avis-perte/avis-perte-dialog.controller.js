(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('AvisPerteDialogController', AvisPerteDialogController);

    AvisPerteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AvisPerte'];

    function AvisPerteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AvisPerte) {
        var vm = this;

        vm.avisPerte = entity;
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
            if (vm.avisPerte.id !== null) {
                AvisPerte.update(vm.avisPerte, onSaveSuccess, onSaveError);
            } else {
                AvisPerte.save(vm.avisPerte, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:avisPerteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
