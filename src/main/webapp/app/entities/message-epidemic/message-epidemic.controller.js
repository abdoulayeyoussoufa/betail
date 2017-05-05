(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('MessageEpidemicController', MessageEpidemicController);

    MessageEpidemicController.$inject = ['MessageEpidemic'];

    function MessageEpidemicController(MessageEpidemic) {

        var vm = this;

        vm.messageEpidemics = [];

        loadAll();

        function loadAll() {
            MessageEpidemic.query(function(result) {
                vm.messageEpidemics = result;
                vm.searchQuery = null;
            });
        }
    }
})();
