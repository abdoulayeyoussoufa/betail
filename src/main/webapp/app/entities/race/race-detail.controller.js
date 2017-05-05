(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('RaceDetailController', RaceDetailController);

    RaceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Race'];

    function RaceDetailController($scope, $rootScope, $stateParams, previousState, entity, Race) {
        var vm = this;

        vm.race = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:raceUpdate', function(event, result) {
            vm.race = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
