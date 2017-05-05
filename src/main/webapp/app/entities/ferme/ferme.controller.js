(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FermeController', FermeController);

    FermeController.$inject = ['Ferme'];

    function FermeController(Ferme) {

        var vm = this;

        vm.fermes = [];

        loadAll();

        function loadAll() {
            Ferme.query(function(result) {
                vm.fermes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
