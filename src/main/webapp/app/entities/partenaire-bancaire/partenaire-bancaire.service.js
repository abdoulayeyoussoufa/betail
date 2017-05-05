(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('PartenaireBancaire', PartenaireBancaire);

    PartenaireBancaire.$inject = ['$resource'];

    function PartenaireBancaire ($resource) {
        var resourceUrl =  'api/partenaire-bancaires/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
