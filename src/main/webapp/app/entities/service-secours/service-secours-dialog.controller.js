(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('ServiceSecoursDialogController', ServiceSecoursDialogController);

    ServiceSecoursDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ServiceSecours'];

    function ServiceSecoursDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ServiceSecours) {
        var vm = this;

        vm.serviceSecours = entity;
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
            if (vm.serviceSecours.id !== null) {
                ServiceSecours.update(vm.serviceSecours, onSaveSuccess, onSaveError);
            } else {
                ServiceSecours.save(vm.serviceSecours, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:serviceSecoursUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
