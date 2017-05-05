(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('PartenaireBancaireDetailController', PartenaireBancaireDetailController);

    PartenaireBancaireDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PartenaireBancaire'];

    function PartenaireBancaireDetailController($scope, $rootScope, $stateParams, previousState, entity, PartenaireBancaire) {
        var vm = this;

        vm.partenaireBancaire = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('volBetailApp:partenaireBancaireUpdate', function(event, result) {
            vm.partenaireBancaire = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
