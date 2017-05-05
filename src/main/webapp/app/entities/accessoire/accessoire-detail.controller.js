(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('AccessoireDetailController', AccessoireDetailController);

    AccessoireDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Accessoire'];

    function AccessoireDetailController($scope, $rootScope, $stateParams, previousState, entity, Accessoire) {
        var vm = this;

        vm.accessoire = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:accessoireUpdate', function(event, result) {
            vm.accessoire = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
