(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('ServiceSecoursDetailController', ServiceSecoursDetailController);

    ServiceSecoursDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ServiceSecours'];

    function ServiceSecoursDetailController($scope, $rootScope, $stateParams, previousState, entity, ServiceSecours) {
        var vm = this;

        vm.serviceSecours = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:serviceSecoursUpdate', function(event, result) {
            vm.serviceSecours = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
