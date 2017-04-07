(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('Ayant_droitDetailController', Ayant_droitDetailController);

    Ayant_droitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ayant_droit', 'Assure'];

    function Ayant_droitDetailController($scope, $rootScope, $stateParams, previousState, entity, Ayant_droit, Assure) {
        var vm = this;

        vm.ayant_droit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mutuplexApp:ayant_droitUpdate', function(event, result) {
            vm.ayant_droit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
