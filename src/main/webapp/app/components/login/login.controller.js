(function() {
    'use strict';

    angular
        .module('volBetailApp')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope','$rootScope', '$state', '$timeout', 'Auth'];

    function LoginController ($scope,$rootScope, $state, $timeout, Auth) {
        var vm = this;

        $scope.authenticationError = false;
        $scope.cancel = cancel;
        $scope.credentials = {};
        $scope.login = login;
        $scope.password = null;
        $scope.register = register;
        $scope.rememberMe = true;
        $scope.requestResetPassword = requestResetPassword;
        $scope.username = null;

        $timeout(function (){angular.element('#username').focus();});

        function cancel () {
            $scope.credentials = {
                username: null,
                password: null,
                rememberMe: true
            };
            $scope.authenticationError = false;
        }

        function login () {
            //event.preventDefault();
        	
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                
                if (Auth.getPreviousState()) {
//                	alert("logiiiiin3");
                    var previousState = Auth.getPreviousState();
                    Auth.resetPreviousState();
                    $state.go(previousState.name, previousState.params);
                }
                $('body').removeClass('gray-bg')
                $state.go('home');
                if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                    $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                    $state.go('home');
                }

                $rootScope.$broadcast('authenticationSuccess');

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is succesful, go to stored previousState and clear previousState
               
            }).catch(function () {
                $scope.authenticationError = true;
            });
            
        }

        function register () {
            $state.go('register');
        }

        function requestResetPassword () {
            $state.go('requestReset');
        }
    }
})();
