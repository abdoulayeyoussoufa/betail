(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('EpidemieController', EpidemieController);

    EpidemieController.$inject = ['Epidemie'];

    function EpidemieController(Epidemie) {

        var vm = this;

        vm.epidemies = [];

        loadAll();

        function loadAll() {
            Epidemie.query(function(result) {
                vm.epidemies = result;
                vm.searchQuery = null;
            });
        }
    }
})();
