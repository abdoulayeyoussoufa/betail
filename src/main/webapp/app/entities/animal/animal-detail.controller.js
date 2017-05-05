(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('AnimalDetailController', AnimalDetailController);

    AnimalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Animal', 'Maladie'];

    function AnimalDetailController($scope, $rootScope, $stateParams, previousState, entity, Animal) {
        var vm = this;

        vm.animal = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:animalUpdate', function(event, result) {
            vm.animal = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
