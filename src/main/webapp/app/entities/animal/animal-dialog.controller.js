(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('AnimalDialogController', AnimalDialogController);

    AnimalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Animal','Maladie','Veterinaire','CarnetSante','FicheAnimal','Troupeau','FicheGeolocalisatio'];

    function AnimalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Animal, Maladie, Veterinaire, CarnetSante, FicheAnimal, Troupeau,FicheGeolocalisatio) {
        var vm = this;

        vm.animal = entity;
        vm.clear = clear;
        vm.save = save;
        vm.maladies = Maladie.query();
        vm.carnetSantes = CarnetSante.query();
        vm.ficheAnimal =  FicheAnimal.query();
        vm.veterinaires = Veterinaire.query();
        vm.troupeaux = Troupeau.query();
        vm.positionsCour = FicheGeolocalisatio.query(); 

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.animal.id !== null) {
                Animal.update(vm.animal, onSaveSuccess, onSaveError);
            } else {
                Animal.save(vm.animal, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('volBetailApp:animalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
