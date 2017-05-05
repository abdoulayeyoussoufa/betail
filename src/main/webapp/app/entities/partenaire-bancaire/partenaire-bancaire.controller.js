(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('PartenaireBancaireController', PartenaireBancaireController);

    PartenaireBancaireController.$inject = ['PartenaireBancaire'];

    function PartenaireBancaireController(PartenaireBancaire) {

        var vm = this;

        vm.partenaireBancaires = [];

        loadAll();

        function loadAll() {
            PartenaireBancaire.query(function(result) {
                vm.partenaireBancaires = result;
                vm.searchQuery = null;
            });
        }
    }
})();
