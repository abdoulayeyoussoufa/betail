(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('MaladieDetailController', MaladieDetailController);

    MaladieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Maladie'];

    function MaladieDetailController($scope, $rootScope, $stateParams, previousState, entity, Maladie) {
        var vm = this;

        vm.maladie = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:maladieUpdate', function(event, result) {
            vm.maladie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
