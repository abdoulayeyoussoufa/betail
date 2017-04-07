(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('PoliceDetailController', PoliceDetailController);

    PoliceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Police'];

    function PoliceDetailController($scope, $rootScope, $stateParams, previousState, entity, Police) {
        var vm = this;

        vm.police = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mutuplexApp:policeUpdate', function(event, result) {
            vm.police = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
