(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('Race', Race);

    Race.$inject = ['$resource'];

    function Race ($resource) {
        var resourceUrl =  'api/races/:id';

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
