(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('LocaliteDetailController', LocaliteDetailController);

    LocaliteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Localite'];

    function LocaliteDetailController($scope, $rootScope, $stateParams, previousState, entity, Localite) {
        var vm = this;

        vm.localite = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:localiteUpdate', function(event, result) {
            vm.localite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
