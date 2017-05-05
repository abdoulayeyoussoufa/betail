(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FactureController', FactureController);

    FactureController.$inject = ['Facture'];

    function FactureController(Facture) {

        var vm = this;

        vm.factures = [];

        loadAll();

        function loadAll() {
            Facture.query(function(result) {
                vm.factures = result;
                vm.searchQuery = null;
            });
        }
    }
})();
