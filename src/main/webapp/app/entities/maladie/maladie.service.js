(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('Maladie', Maladie);

    Maladie.$inject = ['$resource'];

    function Maladie ($resource) {
        var resourceUrl =  'api/maladies/:id';

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
