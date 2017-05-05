(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('MaladieController', MaladieController);

    MaladieController.$inject = ['Maladie'];

    function MaladieController(Maladie) {

        var vm = this;

        vm.maladies = [];

        loadAll();

        function loadAll() {
            Maladie.query(function(result) {
                vm.maladies = result;
                vm.searchQuery = null;
            });
        }
    }
})();
