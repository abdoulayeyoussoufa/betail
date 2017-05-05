(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('PoliceController', PoliceController);

    PoliceController.$inject = ['Police'];

    function PoliceController(Police) {

        var vm = this;

        vm.police = [];

        loadAll();

        function loadAll() {
            Police.query(function(result) {
                vm.police = result;
                vm.searchQuery = null;
            });
        }
    }
})();
