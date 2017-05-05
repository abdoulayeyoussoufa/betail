(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('Betail', Betail);

    Betail.$inject = ['$resource'];

    function Betail ($resource) {
        var resourceUrl =  'api/betails/:id';

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
