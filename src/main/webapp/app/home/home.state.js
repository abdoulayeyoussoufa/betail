(function() {
	'use strict';

	angular
	.module('volBetailApp')
	.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider.state('home', {
			parent: 'app',
			url: '/',
			data: {
				authorities: ['ROLE_USER']
			},
			templateUrl: 'app/home/home.html',
			controller: 'HomeController',
			resolve: {
				mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
					$translatePartialLoader.addPart('home');
					return $translate.refresh();
				}]
			}
		});
	}
})();
