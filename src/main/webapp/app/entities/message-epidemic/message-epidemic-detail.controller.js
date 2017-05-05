(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('MessageEpidemicDetailController', MessageEpidemicDetailController);

    MessageEpidemicDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MessageEpidemic'];

    function MessageEpidemicDetailController($scope, $rootScope, $stateParams, previousState, entity, MessageEpidemic) {
        var vm = this;

        vm.messageEpidemic = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:messageEpidemicUpdate', function(event, result) {
            vm.messageEpidemic = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
