(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('EpidemieDetailController', EpidemieDetailController);

    EpidemieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Epidemie'];

    function EpidemieDetailController($scope, $rootScope, $stateParams, previousState, entity, Epidemie) {
        var vm = this;

        vm.epidemie = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:epidemieUpdate', function(event, result) {
            vm.epidemie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
