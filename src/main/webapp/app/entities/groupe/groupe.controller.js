(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('GroupeController', GroupeController);

    GroupeController.$inject = ['$scope', '$state', 'Groupe'];

    function GroupeController ($scope, $state, Groupe) {
        var vm = this;
        
        vm.groupes = [];

        loadAll();

        function loadAll() {
            Groupe.query(function(result) {
                vm.groupes = result;
            });
        }
    }
})();
