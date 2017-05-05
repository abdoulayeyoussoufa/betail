(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('Personne', Personne);

    Personne.$inject = ['$resource'];

    function Personne ($resource) {
        var resourceUrl =  'api/personnes/:id';

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
