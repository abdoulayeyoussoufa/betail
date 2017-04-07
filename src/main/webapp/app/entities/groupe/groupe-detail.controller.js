(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .controller('GroupeDetailController', GroupeDetailController);

    GroupeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Groupe', 'Police'];

    function GroupeDetailController($scope, $rootScope, $stateParams, previousState, entity, Groupe, Police) {
        var vm = this;

        vm.groupe = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mutuplexApp:groupeUpdate', function(event, result) {
            vm.groupe = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
