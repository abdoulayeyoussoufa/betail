(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('TroupeauController', TroupeauController);

    TroupeauController.$inject = ['Troupeau'];

    function TroupeauController(Troupeau) {

        var vm = this;

        vm.troupeaus = [];

        loadAll();

        function loadAll() {
            Troupeau.query(function(result) {
                vm.troupeaus = result;
                vm.searchQuery = null;
            });
        }
    }
})();
