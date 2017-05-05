(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('BetailDetailController', BetailDetailController);

    BetailDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Betail'];

    function BetailDetailController($scope, $rootScope, $stateParams, previousState, entity, Betail) {
        var vm = this;

        vm.betail = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:betailUpdate', function(event, result) {
            vm.betail = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
