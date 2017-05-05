(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('TroupeauDetailController', TroupeauDetailController);

    TroupeauDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Troupeau'];

    function TroupeauDetailController($scope, $rootScope, $stateParams, previousState, entity, Troupeau) {
        var vm = this;

        vm.troupeau = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:troupeauUpdate', function(event, result) {
            vm.troupeau = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
