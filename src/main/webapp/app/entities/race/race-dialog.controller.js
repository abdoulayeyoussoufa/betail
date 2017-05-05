(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('RaceDialogController', RaceDialogController);

    RaceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Race'];

    function RaceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Race) {
        var vm = this;

        vm.race = entity;
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
            if (vm.race.id !== null) {
                Race.update(vm.race, onSaveSuccess, onSaveError);
            } else {
                Race.save(vm.race, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:raceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
