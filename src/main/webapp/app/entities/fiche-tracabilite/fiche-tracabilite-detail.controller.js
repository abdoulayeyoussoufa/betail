(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheTracabiliteDetailController', FicheTracabiliteDetailController);

    FicheTracabiliteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FicheTracabilite'];

    function FicheTracabiliteDetailController($scope, $rootScope, $stateParams, previousState, entity, FicheTracabilite) {
        var vm = this;

        vm.ficheTracabilite = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:ficheTracabiliteUpdate', function(event, result) {
            vm.ficheTracabilite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
