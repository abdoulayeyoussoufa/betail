(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('VolMessageController', VolMessageController);

    VolMessageController.$inject = ['VolMessage'];

    function VolMessageController(VolMessage) {

        var vm = this;

        vm.volMessages = [];

        loadAll();

        function loadAll() {
            VolMessage.query(function(result) {
                vm.volMessages = result;
                vm.searchQuery = null;
            });
        }
    }
})();
