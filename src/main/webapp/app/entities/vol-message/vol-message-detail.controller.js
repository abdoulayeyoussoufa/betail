(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('VolMessageDetailController', VolMessageDetailController);

    VolMessageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VolMessage'];

    function VolMessageDetailController($scope, $rootScope, $stateParams, previousState, entity, VolMessage) {
        var vm = this;

        vm.volMessage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:volMessageUpdate', function(event, result) {
            vm.volMessage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
