(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('AvisPerteController', AvisPerteController);

    AvisPerteController.$inject = ['AvisPerte'];

    function AvisPerteController(AvisPerte) {

        var vm = this;

        vm.avisPertes = [];

        loadAll();

        function loadAll() {
            AvisPerte.query(function(result) {
                vm.avisPertes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
