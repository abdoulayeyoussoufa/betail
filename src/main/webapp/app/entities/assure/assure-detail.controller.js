(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('AssureDetailController', AssureDetailController);

    AssureDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Assure', 'Police', 'Groupe'];

    function AssureDetailController($scope, $rootScope, $stateParams, previousState, entity, Assure, Police, Groupe) {
        var vm = this;

        vm.assure = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mutuplexApp:assureUpdate', function(event, result) {
            vm.assure = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
