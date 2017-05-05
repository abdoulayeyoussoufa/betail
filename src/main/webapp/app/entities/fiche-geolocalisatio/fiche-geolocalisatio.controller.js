(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheGeolocalisatioController', FicheGeolocalisatioController);

    FicheGeolocalisatioController.$inject = ['FicheGeolocalisatio'];

    function FicheGeolocalisatioController(FicheGeolocalisatio) {

        var vm = this;

        vm.ficheGeolocalisatios = [];

        loadAll();

        function loadAll() {
            FicheGeolocalisatio.query(function(result) {
                vm.ficheGeolocalisatios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
