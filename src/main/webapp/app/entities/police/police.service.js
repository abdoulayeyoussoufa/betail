(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('Police', Police);

    Police.$inject = ['$resource'];

    function Police ($resource) {
        var resourceUrl =  'api/police/:id';

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
