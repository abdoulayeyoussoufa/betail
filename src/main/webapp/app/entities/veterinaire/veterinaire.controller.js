(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('VeterinaireController', VeterinaireController);

    VeterinaireController.$inject = ['Veterinaire'];

    function VeterinaireController(Veterinaire) {

        var vm = this;

        vm.veterinaires = [];

        loadAll();

        function loadAll() {
            Veterinaire.query(function(result) {
                vm.veterinaires = result;
                vm.searchQuery = null;
            });
        }
    }
})();
