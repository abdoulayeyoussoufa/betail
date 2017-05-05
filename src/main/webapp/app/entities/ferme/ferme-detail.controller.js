(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FermeDetailController', FermeDetailController);

    FermeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ferme'];

    function FermeDetailController($scope, $rootScope, $stateParams, previousState, entity, Ferme) {
        var vm = this;

        vm.ferme = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:fermeUpdate', function(event, result) {
            vm.ferme = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
