(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('VeterinaireDetailController', VeterinaireDetailController);

    VeterinaireDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Veterinaire'];

    function VeterinaireDetailController($scope, $rootScope, $stateParams, previousState, entity, Veterinaire) {
        var vm = this;

        vm.veterinaire = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:veterinaireUpdate', function(event, result) {
            vm.veterinaire = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
