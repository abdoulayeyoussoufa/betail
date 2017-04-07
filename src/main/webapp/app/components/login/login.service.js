(function() {
    'use strict';

    angular
        .module('mutuplexApp')
        .factory('LoginService', LoginService);

    //LoginService.$inject = ['$uibModal'];

    function LoginService () {
        var service = {
            open: open
        };

        var modalInstance = null;
        var resetModal = function () {
            modalInstance = null;
        };

        return service;

        function open () {
            if (modalInstance !== null) return;
            $state.go('login');
//            modalInstance = $uibModal.open({
//                animation: true,
//                templateUrl: 'app/components/login/login.html',
//                controller: 'LoginController',
//                controllerAs: 'vm',
//                resolve: {
//                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                        $translatePartialLoader.addPart('login');
//                        return $translate.refresh();
//                    }]
//                }
//            });
//            modalInstance.result.then(
//                resetModal,
//                resetModal
//            );
        }
    }
})();
