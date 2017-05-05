(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('FicheGeolocalisatioDetailController', FicheGeolocalisatioDetailController);

    FicheGeolocalisatioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FicheGeolocalisatio'];

    function FicheGeolocalisatioDetailController($scope, $rootScope, $stateParams, previousState, entity, FicheGeolocalisatio) {
        var vm = this;

        vm.ficheGeolocalisatio = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:ficheGeolocalisatioUpdate', function(event, result) {
            vm.ficheGeolocalisatio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
