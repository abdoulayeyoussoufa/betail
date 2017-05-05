(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('Accessoire', Accessoire);

    Accessoire.$inject = ['$resource'];

    function Accessoire ($resource) {
        var resourceUrl =  'api/accessoires/:id';

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
