(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('GroupeDialogController', GroupeDialogController);

    GroupeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Groupe', 'Police'];

    function GroupeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Groupe, Police) {
        var vm = this;

        vm.groupe = entity;
        vm.clear = clear;
        vm.save = save;
        vm.police = Police.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.groupe.id !== null) {
                Groupe.update(vm.groupe, onSaveSuccess, onSaveError);
            } else {
                Groupe.save(vm.groupe, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mutuplexApp:groupeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
