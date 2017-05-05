(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('TroupeauDialogController', TroupeauDialogController);

    TroupeauDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Troupeau'];

    function TroupeauDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Troupeau) {
        var vm = this;

        vm.troupeau = entity;
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
            if (vm.troupeau.id !== null) {
                Troupeau.update(vm.troupeau, onSaveSuccess, onSaveError);
            } else {
                Troupeau.save(vm.troupeau, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:troupeauUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
