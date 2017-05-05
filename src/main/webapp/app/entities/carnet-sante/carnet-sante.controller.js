(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('CarnetSanteController', CarnetSanteController);

    CarnetSanteController.$inject = ['CarnetSante'];

    function CarnetSanteController(CarnetSante) {

        var vm = this;

        vm.carnetSantes = [];

        loadAll();

        function loadAll() {
            CarnetSante.query(function(result) {
                vm.carnetSantes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
