(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('PartenaireBancaireDialogController', PartenaireBancaireDialogController);

    PartenaireBancaireDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PartenaireBancaire'];

    function PartenaireBancaireDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PartenaireBancaire) {
        var vm = this;

        vm.partenaireBancaire = entity;
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
            if (vm.partenaireBancaire.id !== null) {
                PartenaireBancaire.update(vm.partenaireBancaire, onSaveSuccess, onSaveError);
            } else {
                PartenaireBancaire.save(vm.partenaireBancaire, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:partenaireBancaireUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
