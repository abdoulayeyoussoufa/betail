(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('Ayant_droitController', Ayant_droitController);

    Ayant_droitController.$inject = ['$scope', '$state', 'Ayant_droit'];

    function Ayant_droitController ($scope, $state, Ayant_droit) {
        var vm = this;
        
        vm.ayant_droits = [];

        loadAll();

        function loadAll() {
            Ayant_droit.query(function(result) {
                vm.ayant_droits = result;
            });
        }
    }
})();
