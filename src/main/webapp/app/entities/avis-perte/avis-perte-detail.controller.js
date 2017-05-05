(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('AvisPerteDetailController', AvisPerteDetailController);

    AvisPerteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AvisPerte'];

    function AvisPerteDetailController($scope, $rootScope, $stateParams, previousState, entity, AvisPerte) {
        var vm = this;

        vm.avisPerte = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:avisPerteUpdate', function(event, result) {
            vm.avisPerte = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
