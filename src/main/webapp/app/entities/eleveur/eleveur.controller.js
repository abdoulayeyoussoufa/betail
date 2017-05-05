(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('EleveurController', EleveurController);

    EleveurController.$inject = ['Eleveur'];

    function EleveurController(Eleveur) {

        var vm = this;

        vm.eleveurs = [];

        loadAll();

        function loadAll() {
            Eleveur.query(function(result) {
                vm.eleveurs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
