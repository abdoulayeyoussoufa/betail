(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheTracabiliteController', FicheTracabiliteController);

    FicheTracabiliteController.$inject = ['FicheTracabilite'];

    function FicheTracabiliteController(FicheTracabilite) {

        var vm = this;

        vm.ficheTracabilites = [];

        loadAll();

        function loadAll() {
            FicheTracabilite.query(function(result) {
                vm.ficheTracabilites = result;
                vm.searchQuery = null;
            });
        }
    }
})();
