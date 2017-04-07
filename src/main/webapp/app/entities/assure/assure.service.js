(function() {
    'use strict';
    angular
        .module('mutuplexApp')
        .factory('Assure', Assure);

    Assure.$inject = ['$resource'];

    function Assure ($resource) {
        var resourceUrl =  'api/assures/:id';

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
