(function() {
	'use strict';

	angular
	.module('volBetailApp')
	.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig ($stateProvider) {
		$stateProvider
		.state('login', {
			parent: 'app',
			url: '/login',
			data: {
				roles: [], 
				pageTitle: 'login.title'},
				views: {
					'login@': {
						templateUrl: 'app/components/login/login.html',
						controller: 'LoginController',
						controllerAs: 'vm'
					}
				},
				onEnter: function() {
					$('body').addClass('gray-bg')
				},
				resolve: {
					translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('login');
						$translatePartialLoader.addPart('global');
						return $translate.refresh();
					}]
				}
		});
	}
})();