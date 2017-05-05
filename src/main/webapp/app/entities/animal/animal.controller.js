(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('AnimalController', AnimalController);

    AnimalController.$inject = ['Animal'];

    function AnimalController(Animal) {

        var vm = this;

        vm.animals = [];

        loadAll();

        function loadAll() {
            Animal.query(function(result) {
                vm.animals = result;
                vm.searchQuery = null;
            });
        }
    }
})();
