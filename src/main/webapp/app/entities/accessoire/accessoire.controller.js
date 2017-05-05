(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('AccessoireController', AccessoireController);

    AccessoireController.$inject = ['Accessoire'];

    function AccessoireController(Accessoire) {

        var vm = this;

        vm.accessoires = [];

        loadAll();

        function loadAll() {
            Accessoire.query(function(result) {
                vm.accessoires = result;
                vm.searchQuery = null;
            });
        }
    }
})();
