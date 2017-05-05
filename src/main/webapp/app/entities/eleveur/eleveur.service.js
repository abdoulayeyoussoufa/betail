(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('Eleveur', Eleveur);

    Eleveur.$inject = ['$resource'];

    function Eleveur ($resource) {
        var resourceUrl =  'api/eleveurs/:id';

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
