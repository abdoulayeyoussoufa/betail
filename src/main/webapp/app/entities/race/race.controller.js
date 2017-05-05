(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('RaceController', RaceController);

    RaceController.$inject = ['Race'];

    function RaceController(Race) {

        var vm = this;

        vm.races = [];

        loadAll();

        function loadAll() {
            Race.query(function(result) {
                vm.races = result;
                vm.searchQuery = null;
            });
        }
    }
})();
