(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('CommandeController', CommandeController);

    CommandeController.$inject = ['Commande'];

    function CommandeController(Commande) {

        var vm = this;

        vm.commandes = [];

        loadAll();

        function loadAll() {
            Commande.query(function(result) {
                vm.commandes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
