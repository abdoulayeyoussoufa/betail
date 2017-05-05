(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheAnimalController', FicheAnimalController);

    FicheAnimalController.$inject = ['FicheAnimal'];

    function FicheAnimalController(FicheAnimal) {

        var vm = this;

        vm.ficheAnimals = [];

        loadAll();

        function loadAll() {
            FicheAnimal.query(function(result) {
                vm.ficheAnimals = result;
                vm.searchQuery = null;
            });
        }
    }
})();
