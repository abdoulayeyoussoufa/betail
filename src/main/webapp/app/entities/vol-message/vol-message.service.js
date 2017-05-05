(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('VolMessage', VolMessage);

    VolMessage.$inject = ['$resource'];

    function VolMessage ($resource) {
        var resourceUrl =  'api/vol-messages/:id';

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
