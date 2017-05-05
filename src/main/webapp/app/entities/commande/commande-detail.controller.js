(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('CommandeDetailController', CommandeDetailController);

    CommandeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Commande'];

    function CommandeDetailController($scope, $rootScope, $stateParams, previousState, entity, Commande) {
        var vm = this;

        vm.commande = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:commandeUpdate', function(event, result) {
            vm.commande = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
