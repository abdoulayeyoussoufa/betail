(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('LocaliteController', LocaliteController);

    LocaliteController.$inject = ['Localite'];

    function LocaliteController(Localite) {

        var vm = this;

        vm.localites = [];

        loadAll();

        function loadAll() {
            Localite.query(function(result) {
                vm.localites = result;
                vm.searchQuery = null;
            });
        }
    }
})();
