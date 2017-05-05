(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('PersonneDialogController', PersonneDialogController);

    PersonneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Personne'];

    function PersonneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Personne) {
        var vm = this;

        vm.personne = entity;
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
            if (vm.personne.id !== null) {
                Personne.update(vm.personne, onSaveSuccess, onSaveError);
            } else {
                Personne.save(vm.personne, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:personneUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
