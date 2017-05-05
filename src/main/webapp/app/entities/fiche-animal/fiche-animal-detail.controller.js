(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheAnimalDetailController', FicheAnimalDetailController);

    FicheAnimalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FicheAnimal'];

    function FicheAnimalDetailController($scope, $rootScope, $stateParams, previousState, entity, FicheAnimal) {
        var vm = this;

        vm.ficheAnimal = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:ficheAnimalUpdate', function(event, result) {
            vm.ficheAnimal = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
