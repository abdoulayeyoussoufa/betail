(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('PersonneDetailController', PersonneDetailController);

    PersonneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Personne'];

    function PersonneDetailController($scope, $rootScope, $stateParams, previousState, entity, Personne) {
        var vm = this;

        vm.personne = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:personneUpdate', function(event, result) {
            vm.personne = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
