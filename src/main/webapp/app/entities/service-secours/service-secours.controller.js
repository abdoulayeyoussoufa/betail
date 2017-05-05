(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('ServiceSecoursController', ServiceSecoursController);

    ServiceSecoursController.$inject = ['ServiceSecours'];

    function ServiceSecoursController(ServiceSecours) {

        var vm = this;

        vm.serviceSecours = [];

        loadAll();

        function loadAll() {
            ServiceSecours.query(function(result) {
                vm.serviceSecours = result;
                vm.searchQuery = null;
            });
        }
    }
})();
