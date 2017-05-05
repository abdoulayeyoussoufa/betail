(function() {
    'use strict';
    angular
        .module('volBetailApp')
        .factory('Veterinaire', Veterinaire);

    Veterinaire.$inject = ['$resource'];

    function Veterinaire ($resource) {
        var resourceUrl =  'api/veterinaires/:id';

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
