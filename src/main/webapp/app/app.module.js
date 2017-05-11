(function() {
    'use strict';

    angular
        .module('volBetailApp', [
            'ngStorage', 
            'tmh.dynamicLocale',
            'pascalprecht.translate', 
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'datatables',
            'datatables.bootstrap',
            'datatables.tabletools',
            'datatables.buttons',
            'localytics.directives'
        ])
        .run(run)
        .run(DTDefaultOptionsrun)
        .run(getsate);

    run.$inject = ['stateHandler', 'translationHandler'];
    DTDefaultOptionsrun.$inject = ['DTDefaultOptions'];
    getsate.$inject = ['$rootScope', '$state','$stateParams'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }
    
    function getsate($rootScope, $state, $stateParams){                    
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
    }

	 function DTDefaultOptionsrun(DTDefaultOptions) {
		 var oPaginate = {};
		 oPaginate["sFirst"] = "|<";
		 oPaginate["sPrevious"] = "<";
		 oPaginate["sNext"] = ">";
		 oPaginate["sLast"] = ">|";
		 var oLanguage = {};
		 oLanguage["oPaginate"] = oPaginate;
		 oLanguage["sLengthMenu"] = "Afficher _MENU_ &eacute;l&eacute;ments";
		 oLanguage["sInfo"] = "_START_ &agrave; _END_ sur _TOTAL_ &eacute;l&eacute;ments";
		 oLanguage["sSearch"] = "Recherche";
		 oLanguage["sProcessing"] = "";
		 oLanguage["sZeroRecords"] = "Aucun &eacute;l&eacute;ment &agrave; afficher";
		 oLanguage["sEmptyTable"] = "Aucune donn&eacute;e disponible dans le tableau";
		 oLanguage["sInfoEmpty"] = "0 &eacute;l&eacute;ment &agrave; afficher";
		 oLanguage["sInfoFiltered"] = "";
		 oLanguage["sInfoPostFix"] = "";
		 DTDefaultOptions.setLanguage(oLanguage);
	 }
})();
