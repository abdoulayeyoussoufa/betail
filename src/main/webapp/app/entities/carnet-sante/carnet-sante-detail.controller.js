(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('CarnetSanteDetailController', CarnetSanteDetailController);

    CarnetSanteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CarnetSante'];

    function CarnetSanteDetailController($scope, $rootScope, $stateParams, previousState, entity, CarnetSante) {
        var vm = this;

        vm.carnetSante = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:carnetSanteUpdate', function(event, result) {
            vm.carnetSante = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
