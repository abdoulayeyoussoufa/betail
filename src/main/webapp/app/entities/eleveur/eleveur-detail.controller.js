(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('EleveurDetailController', EleveurDetailController);

    EleveurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Eleveur'];

    function EleveurDetailController($scope, $rootScope, $stateParams, previousState, entity, Eleveur) {
        var vm = this;

        vm.eleveur = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:eleveurUpdate', function(event, result) {
            vm.eleveur = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
